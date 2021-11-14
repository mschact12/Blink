/*
 * -----------------------
 * --- DEVICE HANDLER ----
 * -----------------------
 *
 */

/* **DISCLAIMER**
 * THIS SOFTWARE IS PROVIDED "AS IS" AND ANY EXPRESSED OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION)
 * HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 * Without limitation of the foregoing, Contributors/Regents expressly does not warrant that:
 * 1. the software will meet your requirements or expectations;
 * 2. the software or the software content will be free of bugs, errors, viruses or other defects;
 * 3. any results, output, or data provided through or generated by the software will be accurate, up-to-date, complete or reliable;
 * 4. the software will be compatible with third party software;
 * 5. any errors in the software will be corrected.
 * The user assumes all responsibility for selecting the software and for the results obtained from the use of the software. The user shall bear the entire risk as to the quality and the performance of the software.
 */ 
 
/**
 * Virtual Blink Sync Module Switch
 * 
 * Copyright RBoy Apps
 * Change log:
 * 2020-05-05 - (v01.01.00) Updated to reset on initialize
 * 2018-08-05 - (v01.00.00) Initial public release
 *
 */


metadata {
	definition (name: "RBoy Apps Virtual Blink Sync Module Switch", namespace: "rboy", author: "RBoy Apps", ocfDeviceType: "oic.d.switch", mnmn: "SmartThings", vid:"generic-switch") {
        capability "Actuator"
        capability "Switch"
        capability "Relay Switch"
        capability "Health Check"
    }

    tiles(scale: 2) {
        multiAttributeTile(name:"summary", type: "generic", width: 6, height: 4, canChangeIcon: true) {
            tileAttribute ("device.switch", key: "PRIMARY_CONTROL") {
                attributeState "off", label:'Disarmed', action: "switch.on", icon: "https://www.rboyapps.com/images/SystemOff.png", backgroundColor:"#ffffff"
                attributeState "on", label:'Armed', action: "switch.off", icon: "https://www.rboyapps.com/images/SystemOn.png", backgroundColor:"#00a0dc"
            }
        }
        standardTile("switch", "device.switch", width: 4, height: 2, canChangeIcon: true, decoration: "flat") {
            state "off", label: "Disarmed", action: "switch.on", icon: "https://www.rboyapps.com/images/SystemOff.png", backgroundColor: "#ffffff"
            state "on", label: "Armed", action: "switch.off", icon: "https://www.rboyapps.com/images/SystemOn.png", backgroundColor: "#00a0dc"
        }
        
        main "summary"
        details(["summary"])
    }
}

def installed() {
    // Device-Watch simply pings if no device events received for checkInterval duration of 32min = 2 * 15min + 2min lag time
	sendEvent(name: "checkInterval", value: 2 * 15 * 60 + 2 * 60, displayed: false, data: [protocol: "lan", hubHardwareId: device.hub?.hardwareID, offlinePingable: "1"])
    off() // Reset it
}

def updated() {
    // Device-Watch simply pings if no device events received for checkInterval duration of 32min = 2 * 15min + 2min lag time
	sendEvent(name: "checkInterval", value: 2 * 15 * 60 + 2 * 60, displayed: false, data: [protocol: "lan", hubHardwareId: device.hub?.hardwareID, offlinePingable: "1"])
}

def ping() {
    sendEvent(name: "switch", value: device.currentValue("switch"))
}

def on() {
	def msg = "${device.displayName} sync module is being armed"
    log.info msg
	sendEvent(name: "switch", value: "on", descriptionText: msg)
}

def off() {
	def msg = "${device.displayName} sync module is being disarmed"
    log.info msg
	sendEvent(name: "switch", value: "off", descriptionText: msg)
}