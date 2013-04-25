#----IMPORTS----------------
import threading
import xml.etree.ElementTree as ET
import socket
import time
import math
import copy

#----KUKA REAL TIME THREAD----
#
class kukaRealTimeThread(threading.Thread):
        def __init__(self, interface = None):
                threading.Thread.__init__(self)
                self.interface = interface
                self.daemon = True
                self.start()
                
        def run(self):
                
                interpolatorTime = 0.012        #12ms
                
                xPosition = 0.0
                yPosition = 0.0
                zPosition = 0.0
                aPosition = 0.0
                bPosition = 0.0
                cPosition = 0.0
                
                xOffset = 0.0
                yOffset = 0.0
                zOffset = 0.0
                aOffset = 0.0
                bOffset = 0.0
                cOffset = 0.0
                
                capturePosition.set(True)
                captureCounter = 5
                
                                                
                #Realtime loop. This gets triggered every 12ms by the RSI Ethernet Object.
                while True:
                        #get packet from KUKA
                        (inboundPacket, (inboundAddress, inboundPort)) = self.interface.receive()
                        xmlFromKuka = parseXMLString(inboundPacket)
                        interpolatorMark = xmlFromKuka['IPOC']['value']
                        if 'XACT' in xmlFromKuka: xActualPosition.set(round(float(xmlFromKuka['XACT']['value']),2))
                        if 'YACT' in xmlFromKuka: yActualPosition.set(round(float(xmlFromKuka['YACT']['value']),2))
                        if 'ZACT' in xmlFromKuka: zActualPosition.set(round(float(xmlFromKuka['ZACT']['value']),2))
                        if 'AACT' in xmlFromKuka: aActualPosition.set(round(float(xmlFromKuka['AACT']['value']),2))
                        if 'BACT' in xmlFromKuka: bActualPosition.set(round(float(xmlFromKuka['BACT']['value']),2))
                        if 'CACT' in xmlFromKuka: cActualPosition.set(round(float(xmlFromKuka['CACT']['value']),2))
                        
                        captureFlag = capturePosition.get()
                        
                        if captureFlag:
                                if 'XACT' in xmlFromKuka: xOffset = xActualPosition.get()
                                if 'YACT' in xmlFromKuka: yOffset = yActualPosition.get()
                                if 'ZACT' in xmlFromKuka: zOffset = zActualPosition.get()
                                if 'AACT' in xmlFromKuka: aOffset = aActualPosition.get()
                                if 'BACT' in xmlFromKuka: bOffset = bActualPosition.get()
                                if 'CACT' in xmlFromKuka: cOffset = cActualPosition.get()
                                if captureCounter:
                                        capturePosition.set(True)
                                        captureCounter -= 1
                                else: capturePosition.set(False)
                        
                        if 'ST_STATUS' in xmlFromKuka:
                                status = int(xmlFromKuka['ST_STATUS']['value'])
                                if status&(1): statusReport.set("STATUS: INTERPOLATOR NORMAL")
                                if status&(2): statusReport.set("STATUS: START WAS RESTART AFTER STOP")
                                if status&(4): statusReport.set("STATUS: PATHSTOP NORMAL")
                                if status&(8): statusReport.set("STATUS: PATHSTOP FAST")
                                if status&(16): statusReport.set("STATUS: ENERGY STOP")
                                if status&(32): statusReport.set("STATUS: ENERGY STOP, ROBOT IN MOTION")
                                if status&(64): statusReport.set("STATUS: CURRENT MOVEMENT IS CARTESIAN")
                                if status&(128): statusReport.set("STATUS: INTERPOLATOR STILL SMOOTHING")
                                
                        
                        #----INTERPOLATOR----
                        #take snapshot of shared variables
                        xCommand = float(xCommandPosition.get())
                        yCommand = float(yCommandPosition.get())
                        zCommand = float(zCommandPosition.get())
                        aCommand = float(aCommandPosition.get())
                        bCommand = float(bCommandPosition.get())
                        cCommand = float(cCommandPosition.get())
                        velocity = float(commandVelocity.get())
                        omega    = float(commandOmega.get())
                        
                        #take deltas
                        xDelta = xCommand - xPosition - xOffset
                        yDelta = yCommand - yPosition - yOffset
                        zDelta = zCommand - zPosition - zOffset
                        aDelta = aCommand - aPosition - aOffset
                        bDelta = bCommand - bPosition - bOffset
                        cDelta = cCommand - cPosition - cOffset
                        
                        linearLength = math.sqrt(math.pow(xDelta, 2) + math.pow(yDelta, 2) + math.pow(zDelta, 2))
                        angularLength = math.sqrt(math.pow(aDelta, 2) + math.pow(bDelta, 2) + math.pow(cDelta, 2))
                        
                        if velocity !=0: linearTime = linearLength / velocity
                        else: linearTime = 0
                        
                        if omega !=0: angularTime = angularLength / omega
                        else: angularTime = 0
                        
                        if angularTime > linearTime: maxTime = angularTime
                        else: maxTime = linearTime
                        
                        if maxTime > 0: 
                                movePercent = interpolatorTime / maxTime
                        else:
                                movePercent = 0
                        
                        xPosition += round(xDelta * movePercent, 2)
                        yPosition += round(yDelta * movePercent, 2)
                        zPosition += round(zDelta * movePercent, 2)
                        aPosition += round(aDelta * movePercent, 5)
                        bPosition += round(bDelta * movePercent, 5)
                        cPosition += round(cDelta * movePercent, 5)
                        
                        
                        #generate outbound packet
                        print "X COMD: " + str(xCommand)
                        print "Y COMD: " + str(yCommand)
                        print "Z COMD: " + str(zCommand)
                        print "A COMD: " + str(aCommand)
                        print "B COMD: " + str(bCommand)
                        print "C COMD: " + str(cCommand)
                        print " "

                        print "X: " + str(xPosition + xOffset)
                        print "Y: " + str(yPosition + yOffset)
                        print "Z: " + str(zPosition + zOffset)
                        print "A: " + str(aPosition + aOffset)
                        print "B: " + str(bPosition + bOffset)
                        print "C: " + str(cPosition + cOffset)
                        print " "
                        
                        #compose and send outbound packet
                        xmlToKuka = generateXMLString({'IPOC':{'attributes':{}, 'value':interpolatorMark},
                                                                                   'XCOR': {'attributes':{},'value':xPosition},
                                                                                   'YCOR': {'attributes':{},'value':yPosition},
                                                                                   'ZCOR': {'attributes':{},'value':zPosition},
                                                                                   'ACOR': {'attributes':{},'value':aPosition},
                                                                                   'BCOR': {'attributes':{},'value':bPosition},
                                                                                   'CCOR': {'attributes':{},'value':cPosition}}, "SEN", {'Type':'pyKUKA'})      
                        
                        self.interface.transmit(inboundAddress, inboundPort, xmlToKuka)
                        
                        if terminateRequest.get():
                                self.interface.close()
                                time.sleep(0.5)
                                terminateReady.set(True)
                                

#----PYTHON INTERFACE THREAD----
class pythonInterfaceThread(threading.Thread):
        def __init__(self):
                threading.Thread.__init__(self)
                self.daemon = True
                self.interface = socketTCPServer(IPAddress = 'localhost', IPPort = 27273)
                self.start()
        
        def run(self):
                while True:
                        data, connection = self.interface.receive()     #wait for incoming command
                        
##                        print data
                        if "TEST" in data:
                                self.interface.transmit(connection, data)
                        else:
                                commandDict = dict(eval(str(data)))     #convert incoming packet, which should be a text-encoded dictionary, back into a dictionary

                                xCommandPosition.set(commandDict['x'])
                                yCommandPosition.set(commandDict['y']) 
                                zCommandPosition.set(commandDict['z'])
                                aCommandPosition.set(commandDict['a'])
                                bCommandPosition.set(commandDict['b'])
                                cCommandPosition.set(commandDict['c']) 
                                commandVelocity.set(commandDict['v'])
                                commandOmega.set(commandDict['w'])
                                
                                if 'terminate' in commandDict:
                                        self.interface.close()
                                        time.sleep(1)
                                        terminateRequest.set(True)
                                
                                if 'capturePosition' in commandDict:
                                        capturePosition.set(True)
                                ######
                                outData = str(statusReport.get()) + "|" +\
                                          str(xActualPosition.get()) + "|" +\
                                          str(yActualPosition.get()) + "|" +\
                                          str(zActualPosition.get()) + "|" +\
                                          str(aActualPosition.get()) + "|" +\
                                          str(bActualPosition.get()) + "|" +\
                                          str(cActualPosition.get()) + "\n"
                                print outData
                                self.interface.transmit(connection, outData)
                                ######

#----THREAD SAFE SHARED VARIABLES----
class sharedVariable(object):
        def __init__(self, initialValue = None):
                self.lock = threading.Lock()
                self.contents = initialValue
        
        def set(self, newContents):
                self.lock.acquire()     #get lock
                self.contents = copy.copy(newContents)
                self.lock.release()
        
        def get(self):
                self.lock.acquire()
                returnValue = copy.copy(self.contents)
                self.lock.release()
                return returnValue
                


#----SOCKET UDP SERVER----
class socketUDPServer(object):
        def __init__(self, IPAddress = '192.168.10.3', IPPort = 27272):
                self.IPAddress = IPAddress
                self.IPPort = IPPort
                self.kukaSocket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)      #UDP, would be socket.SOCK_STREAM for TCP
                self.kukaSocket.bind((self.IPAddress, self.IPPort))     #bind to socket
                print "socketUDPServer: opened socket on " + str(self.IPAddress) + " port " + str(self.IPPort)
        
        def receive(self):
                data, addr = self.kukaSocket.recvfrom(1024)
                print "socketUDPServer: received '" + str(data) + "' from " + str(addr)
                return (data, addr)
        
        def transmit(self, remoteIPAddress, remoteIPPort, data):
                self.kukaSocket.sendto(data, (remoteIPAddress, remoteIPPort))
                
        def close(self):
                self.kukaSocket.close()
                
class socketTCPServer(object):
        def __init__(self, IPAddress = 'localhost', IPPort = 27273):
                self.IPAddress = IPAddress
                self.IPPort = IPPort
                self.pythonSocket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)   #TCP/IP
                self.pythonSocket.bind((self.IPAddress, self.IPPort))
                self.connection = None
        
        def receive(self):
                if self.connection:
                        data = self.connection.recv(1024)
                        if data != "":
                                print "socketTCPServer: received '" + str(data) + "' from " + str(self.address)
                                return data, self.connection
                        else: 
                                self.connection = None
                                print "socketTCPServer: disconnected"
                                
                if not self.connection:
                        self.pythonSocket.listen(1)
                        self.connection, self.address = self.pythonSocket.accept()
                        data = self.connection.recv(1024)
                        print "socketTCPServer: received '" + str(data) + "' from " + str(self.address)
                        return data, self.connection
                
        def transmit(self, connection, data):
                connection.send(data)
                
        def close(self):
                self.pythonSocket.close()
                
#----XML INTERFACE----
def parseXMLString(inputXML):
        rootElement = ET.fromstring(inputXML)
        outputDictionary = {}
        for child in rootElement:
                outputDictionary.update({child.tag:{'attributes':child.attrib, 'value':child.text}})
        return outputDictionary

def generateXMLString(inputDictionary, rootTag = "SEN", rootAttrib = {}):
        rootElement = ET.Element(rootTag, rootAttrib)
        for key in inputDictionary:
                subElement = ET.SubElement(rootElement, key, inputDictionary[key]['attributes'])
                subElement.text = str(inputDictionary[key]['value'])
        return ET.tostring(rootElement)

if __name__ == "__main__":
        
        #define shared variables
        xCommandPosition = sharedVariable(0)
        yCommandPosition = sharedVariable(0)
        zCommandPosition = sharedVariable(0)
        aCommandPosition = sharedVariable(0)
        bCommandPosition = sharedVariable(0)
        cCommandPosition = sharedVariable(0)
        
        commandVelocity = sharedVariable(0)
        commandOmega = sharedVariable(0)
        
        xActualPosition = sharedVariable(0)
        yActualPosition = sharedVariable(0)
        zActualPosition = sharedVariable(0)
        aActualPosition = sharedVariable(0)
        bActualPosition = sharedVariable(0)
        cActualPosition = sharedVariable(0)

        statusReport = sharedVariable("Kopiso")
        
        terminateRequest = sharedVariable(False)
        terminateReady = sharedVariable(False)
        
        capturePosition = sharedVariable(False)
        
        interface = socketUDPServer(IPAddress = '192.168.10.3', IPPort = 27272)
        kuka = kukaRealTimeThread(interface)
        pythonThread = pythonInterfaceThread()
        
        while terminateReady.get() == False:
                time.sleep(1)
