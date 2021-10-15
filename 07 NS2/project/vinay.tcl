# Define options
set val(chan)           Channel/WirelessChannel    ;# channel type
set val(prop)           Propagation/FreeSpace   ;# radio-propagation model
set val(netif)          Phy/WirelessPhy            ;# network interface type
set val(mac)            Mac/802_11                 ;# MAC type
set val(ifq)            Queue/DropTail/PriQueue               ;# interface queue type
set val(ll)             LL                         ;# link layer type
set val(ant)            Antenna/OmniAntenna        ;# antenna model
set val(ifqlen)         10000                         ;# max packet in ifq
set val(nn)             5                         ;# number of mobilenodes
set val(rp)             DSR                       ;# routing protocol
set val(x)              600                ;# X dimension of topography
set val(y)             600              ;# Y dimension of topography  
set val(stop)       100             ;# time of simulation end
set val(R)         300
set opt(tr)     out.tr
set ns        [new Simulator]
set tracefd  [open $opt(tr) w]
set windowVsTime2 [open win.tr w] 
set namtrace      [open simwrls.nam w]    
  Mac/802_11 set dataRate_        1.2e6
Mac/802_11 set RTSThreshold_    100
$ns trace-all $tracefd
#$ns use-newtrace 
$ns namtrace-all-wireless $namtrace $val(x) $val(y)

# set up topography object
set topo       [new Topography]

$topo load_flatgrid $val(x) $val(y)

create-god $val(nn)

#
#  Create nn mobilenodes [$val(nn)] and attach them to the channel. 
#

# configure the nodes

     $ns node-config -adhocRouting $val(rp) \
             -llType $val(ll) \
             -macType $val(mac) \
             -ifqType $val(ifq) \
             -ifqLen $val(ifqlen) \
             -antType $val(ant) \
             -propType $val(prop) \
             -phyType $val(netif) \
             -channelType $val(chan) \
             -topoInstance $topo \
             -agentTrace ON \
             -routerTrace ON \
             -macTrace ON \
             -movementTrace ON

Phy/WirelessPhy set CSThresh 30.5e-10
    for {set i 0} {$i < $val(nn) } { incr i } {
        set node_($i) [$ns node]    
    }
$node_(0) set X_ $val(R) 
$node_(0) set Y_ $val(R) 
$node_(0) set Z_ 0
$node_(1) set X_ $val(R)
$node_(1) set Y_ 0
$node_(1) set Z_ 0
$node_(2) set X_ 0
$node_(2) set Y_ $val(R)
$node_(2) set Z_ 0
$node_(3) set X_ [expr $val(R) *2]
$node_(3) set Y_ $val(R)
$node_(3) set Z_ 0
$node_(4) set X_ $val(R)
$node_(4) set Y_ [expr $val(R) *2]
$node_(4) set Z_ 0
 for {set i 0} {$i<$val(nn)} {incr i} {
  $ns initial_node_pos $node_($i) 30
 }
# Generation of movements
 $ns at 0 "$node_(1) setdest $val(R) $val(R) 3.0"
$ns at 0 "$node_(2) setdest $val(R) $val(R) 3.0"
$ns at 0 "$node_(3) setdest $val(R) $val(R) 3.0"
$ns at 0 "$node_(4) setdest $val(R) $val(R) 3.0"
# Set a TCP connection between node_(0) and node_(1)
set tcp [new Agent/TCP/Newreno]
#$tcp set class_ 2
set tcp [new Agent/UDP]
$tcp set class_ 2
set sink [new Agent/Null]
$ns attach-agent $node_(1) $tcp
$ns attach-agent $node_(0) $sink
$ns connect $tcp $sink
set ftp [new Application/Traffic/CBR]
$ftp attach-agent $tcp
$ns at 0.0 "$ftp start"
# ################################################
# For coloring but doesnot work
# ################################################
$tcp set fid_ 1
$ns color 1 blue
#/////////////////////////////////////////////////
set tcp [new Agent/UDP]
$tcp set class_ 2
set sink [new Agent/Null]
$ns attach-agent $node_(2) $tcp
$ns attach-agent $node_(0) $sink
$ns connect $tcp $sink
set ftp [new Application/Traffic/CBR]
$ftp attach-agent $tcp
$ns at  0.0 "$ftp start"
set tcp [new Agent/UDP]
$tcp set class_ 2
set sink [new Agent/Null]
$ns attach-agent $node_(3) $tcp
$ns attach-agent $node_(0) $sink
$ns connect $tcp $sink
set ftp [new Application/Traffic/CBR]
$ftp attach-agent $tcp
$ns at  0.0 "$ftp start"
set tcp [new Agent/UDP]
$tcp set class_ 2
set sink [new Agent/Null]
$ns attach-agent $node_(4) $tcp
$ns attach-agent $node_(0) $sink
$ns connect $tcp $sink
set ftp [new Application/Traffic/CBR]
$ftp attach-agent $tcp
$ns at  0.0 "$ftp start"
# Telling nodes when the simulation ends
#for {set i 0} {$i < $val(nn) } { incr i } {
 #   $ns at $val(stop) "$node_($i) reset";
#}

# ending nam and the simulation 
$ns at $val(stop) "$ns nam-end-wireless $val(stop)"
$ns at $val(stop) "stop"
$ns at $val(stop) "puts \"end simulation\" ; $ns halt"
proc stop {} {
 exec awk -f fil.awk out.tr > out.xgr
exec xgraph out.xgr &

    global ns tracefd namtrace
    $ns flush-trace
    close $tracefd
   close $namtrace
    exec nam simwrls.nam &
}

$ns run
