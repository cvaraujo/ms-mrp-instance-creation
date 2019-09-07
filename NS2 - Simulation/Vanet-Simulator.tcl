# ======================================================================
# Global options
# ======================================================================
set opt(chan)           Channel/WirelessChannel  ;# channel type
set opt(prop)           Propagation/Nakagami     ;# radio-propagation model
set opt(netif)          Phy/WirelessPhyExt       ;# network interface type
set opt(mac)            Mac/802_11Ext            ;# MAC type
set opt(ifq)            Queue/DropTail/PriQueue  ;# interface queue type
set opt(ll)             LL                       ;# link layer type
set opt(ant)            Antenna/OmniAntenna      ;# antenna model
set opt(adhocRouting)   OLSR                     ;# routing protocol
set opt(ifqlen)         50                       ;# max packet in ifq
set opt(stop)			250						 ;# simulation end time
set opt(cbr-rate) 		200kbps				      ;# CBR data rate

set opt(seed)           1.0                      ;# seed for random number gen.
if {$opt(seed) > 0} {
    puts "Seeding Random number generator with $opt(seed)\n"
    ns-random $opt(seed)
}
# ============================================================================


# ======================================================================
# create simulator instance
# ======================================================================
set ns_ [new Simulator]
# ======================================================================

# ======================================================================
# Input arguments
# ======================================================================
if { $argc >= 3 } {
	set opt(nn)   [lindex $argv 0];		# Number of mobile nodes
	set opt(x)    [lindex $argv 1];     # the width (x-axis) of the simulation
	set opt(y)    [lindex $argv 2];     # the height (Y-axis) of the simulation
	set opt(sc)   [lindex $argv 3];		# Mobility model file mobmod-ZZZ.tcl
	set opt(cp)   [lindex $argv 4];		# Network workload file flowmod-ZZZ.tcl
	set tracefile [lindex $argv 5];   	#"./TraceFile/washington-50-10-25.tr"	 ;# trace file
    set namfile	  [lindex $argv 6];		#"./NAM/washington-50-10-25-visualization.nam"	 ;# nam trace file
} else {
    $ns_ halt
}

# ======================================================================
# Define IEEE 802.11P
# ======================================================================
Phy/WirelessPhyExt set CSThresh_                3.162e-12   ;#-85 dBm Wireless interface sensitivity (sensitivity defined in the standard)
Phy/WirelessPhyExt set Pt_                      0.005         
Phy/WirelessPhyExt set freq_                    5.85e+9; #5.9e+9
Phy/WirelessPhyExt set noise_floor_             1.26e-13    ;#-99 dBm for 10MHz bandwidth
Phy/WirelessPhyExt set L_                       1.0         ;#default radio circuit gain/loss
Phy/WirelessPhyExt set PowerMonitorThresh_      6.310e-14   ;#-102dBm power monitor  sensitivity
Phy/WirelessPhyExt set HeaderDuration_          0.000040    ;#40 us
Phy/WirelessPhyExt set BasicModulationScheme_   0
Phy/WirelessPhyExt set PreambleCaptureSwitch_   1
Phy/WirelessPhyExt set DataCaptureSwitch_       0
Phy/WirelessPhyExt set SINR_PreambleCapture_    2.5118;     ;# 4 dB
Phy/WirelessPhyExt set SINR_DataCapture_        100.0;      ;# 10 dB
Phy/WirelessPhyExt set trace_dist_              1e2         ;# PHY trace until distance of 1 Mio. km ("infinty")
Phy/WirelessPhyExt set PHY_DBG_                 0
Phy/WirelessPhyExt set bandwidth_ 70e2; #DEL PAPER

Mac/802_11Ext set CWMin_                        15
Mac/802_11Ext set CWMax_                        512
Mac/802_11Ext set SlotTime_                     0.000013
Mac/802_11Ext set SIFS_                         0.000032
Mac/802_11Ext set ShortRetryLimit_              7
Mac/802_11Ext set LongRetryLimit_               4
Mac/802_11Ext set HeaderDuration_               0.000040
Mac/802_11Ext set SymbolDuration_               0.000008
Mac/802_11Ext set BasicModulationScheme_        0
Mac/802_11Ext set use_802_11a_flag_             true
Mac/802_11Ext set RTSThreshold_                 2346
Mac/802_11Ext set MAC_DBG                       0
# ======================================================================


# ======================================================================
# Urban propagation model: Nakagami
# ======================================================================
Propagation/Nakagami set use_nakagami_dist_ true 
Propagation/Nakagami set gamma0_ 2.0 
Propagation/Nakagami set gamma1_ 2.0 
Propagation/Nakagami set gamma2_ 2.0 
Propagation/Nakagami set d0_gamma_ 100 
Propagation/Nakagami set d1_gamma_ 400 
Propagation/Nakagami set m0_  1.0 
Propagation/Nakagami set m1_  1.0 
Propagation/Nakagami set m2_  1.0 
Propagation/Nakagami set d0_m_ 80 
Propagation/Nakagami set d1_m_ 200 

# ======================================================================
# Routing protocol configuration: OLSR
# ======================================================================
# OLSR 
set options(HELLO_INTERVAL_) 1.0;
set options(REFRESH_INTERVAL_) 1.0;
set options(TC_INTERVAL_) 2.0;
set options(WILLINGNESS_) 2.0; 
set options(OLSR_NEIGHB_HOLD_TIME_) 3.0;
set options(OLSR_TOP_HOLD_TIME_) 10.0;
set options(OLSR_DUP_HOLD_TIME_) 20.0;
set options(OLSR_MID_HOLD_TIME_) 10.0;

Agent/OLSR set use_mac_    true
Agent/OLSR set debug_      false
Agent/OLSR set willingness $options(WILLINGNESS_)
Agent/OLSR set hello_ival_ $options(HELLO_INTERVAL_)
Agent/OLSR set tc_ival_    $options(TC_INTERVAL_)
Agent/OLSR set mid_ival_   $options(REFRESH_INTERVAL_) 
Agent/OLSR set OLSR_NEIGHB_HOLD_TIME $options(OLSR_NEIGHB_HOLD_TIME_)
Agent/OLSR set OLSR_TOP_HOLD_TIME $options(OLSR_TOP_HOLD_TIME_)
Agent/OLSR set OLSR_DUP_HOLD_TIME $options(OLSR_DUP_HOLD_TIME_)
Agent/OLSR set OLSR_MID_HOLD_TIME $options(OLSR_MID_HOLD_TIME_)
# ======================================================================

# ======================================================================
# Open ns-2 traces
# ======================================================================
    
set tracefd [open $tracefile w]
$ns_ trace-all $tracefd

set namtrace [open $namfile w]
$ns_ namtrace-all-wireless $namtrace $opt(x) $opt(y)
# ======================================================================

#
# create topography object
#
set topo [new Topography]

#
# define topology
#
$topo load_flatgrid $opt(x) $opt(y)

#
# create God
#
create-god $opt(nn)

# ======================================================================
# configure mobile nodes
# ======================================================================
set chan_1_ [new $opt(chan)]
$ns_ node-config -adhocRouting $opt(adhocRouting) \
                 -llType $opt(ll) \
                 -macType $opt(mac) \
                 -ifqType $opt(ifq) \
                 -ifqLen $opt(ifqlen) \
                 -antType $opt(ant) \
                 -propType $opt(prop) \
                 -phyType $opt(netif) \
                 -channel $chan_1_ \
                 -topoInstance $topo \
                 -wiredRouting OFF \
 		 		-agentTrace ON \
 		 		-routerTrace ON \
 		 		-macTrace ON \
 		 		-movementTrace ON
for {set i 0} {$i < $opt(nn)} {incr i} {
	set node_($i) [$ns_ node]
	$node_($i) random-motion 0 ;# disable random motion
	#$ns_ initial_node_pos $node_($i) 50
	$ns_ at $opt(stop).9 "$node_($i) reset"; # tell all nodes when the simulation ends
}
# ======================================================================

# ======================================================================
# source connection-pattern and node-movement scripts
# ======================================================================
if { $opt(cp) == "" } {
    puts "*** NOTE: no connection pattern specified."
    set opt(cp) "none"
} else {
    puts "Loading connection pattern...$opt(cp)"
    source $opt(cp)
}
if { $opt(sc) == "" } {
    puts "*** NOTE: no scenario file specified."
    set opt(sc) "none"
} else {
    puts "Loading scenario file...$opt(sc)"
    source $opt(sc)
    puts "Load complete..."
}
# ======================================================================

# ======================================================================
# stop simulation definition
# ======================================================================
$ns_ at $opt(stop).0001 "stop"

proc stop {tracefile namfile} {
    global ns_ tracefd namtrace
    $ns_ flush-trace
    close $tracefd
	close $namtrace
	$ns_ halt
}

proc UniformErrorProc {} { 
      puts "-----------------------------"
      set err [new ErrorModel] 
      $err unit pkt 
  
      $err set rate_ 1.0
  
      return $err
}

proc finish {} {
    exec xgraph simple.tr -geometry 1000*1000
    exit 0
}
# ======================================================================

# ======================================================================
# begin simulation
# ======================================================================
puts "Starting Simulation..."
$ns_ run
# ======================================================================
