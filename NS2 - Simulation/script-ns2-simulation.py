import os
import subprocess

#begin = 30
#end = 45
#nn = 30
#x = {10 : 324394.32, 
     #15 : 324394.32,
     #20 : 324392.9,
     #30 : 324397.65,
     #40 : 324392.58,
     #50 : 324348.52,
     #60 : 324394.32,
     #70 : 324394.32,
     #80 : 324394.32,
     #90 : 324380.87,
     #100 : 324325.25}

#y = {10 : 4309222.42,
     #15 : 4309387.13,
     #20 : 4309393.26,
     #30 : 4309393.26,
     #40 : 4309413.55,
     #50 : 4309481.62,
     #60 : 4309483.14,
     #70 : 4309458.25,
     #80 : 4309397.72,
     #90 : 4309436.18,
     #100 : 4309483.26}
commands = []

x = 325397.65
y = 4408444.22
path = '../Washington/Maps\ -\ Cleared/Washington\ 50x50/FCD\ -\ Chunck/'
nodes = 70
for nodes in range(70, 110, 10):
	commands.append('ns Vanet-Simulator.tcl ' + str(nodes) + ' ' + str(x) + ' ' + str(y) + ' ' + path+ 'Mobility/mobility-washington-50-' + str(nodes) +'.tcl ' + path + 'Flow/flowmod-washington-50-' + str(nodes) + '.tcl ' + path + '../../../../../../../Documentos/washington-tracefile-50-' + str(nodes) +'.tr ' + path + '../../../../../../../Documentos/washington-visualization-50.nam')

# ns Vanet-Simulator.tcl 10 319685.21 4313764.32 washington_mob_50_65_10.tcl washington_flow_50_65_10.tcl washington-test-10.tr washington-test-10-n.nam

#for i in range(8):
    #inst = 'ns Vanet-Simulator.tcl ' + str(nn) + ' ' + str(x[begin]) + ' ' + str(y[begin]) + ' ' + path + str(begin) + '-' + str(end) + '/mobility-washington-50-' + str(begin) + '-' + str(end) + '-' + str(nn) + '-mobility.tcl ' + path + str(begin) + '-' + str(end) + '/flowmod-washington-50-' + str(begin) + '-' + str(end) + '-' + str(nn) + '.tcl ' + path + str(begin) + '-' + str(end) + '/washington-tracefile-50-'+ str(begin) + '-' + str(end) + '-' + str(nn) + '.tr ' + path + str(begin) + '-' + str(end) + '/washington-namfile-50-'+ str(begin) + '-' + str(end) + '-' + str(nn) + '.nam' 
    #commands.append(inst)
    #begin += 10
    #end += 10

for c in commands:
    print(c)
    p = subprocess.Popen(c, shell=True, stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
    msg, err = p.communicate()
    if msg:
        print(msg)
    print("OK!!")


