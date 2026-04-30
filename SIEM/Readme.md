# Splunk SIEM

In this project, a SIEM solution is implemented using Splunk. It enables the collection and analysis of Application, Security, and System logs from a virtual machine, allowing the evaluation of system behavior under simulated attack scenarios.


## Diagram
<img width="1605" height="1040" alt="SIEM" src="https://github.com/user-attachments/assets/20032cbc-446d-4b17-8408-5ebdf48f929c" />


## Set up
To build the SIEM, two virtual machines were used:

### Server (Ubuntu):
A virtual machine running Ubuntu was configured as the Splunk server. Through a local network, this server enables data ingestion, visualization, and analysis.

<img width="2417" height="538" alt="image" src="https://github.com/user-attachments/assets/18cbcddc-267d-4b4b-b5fa-85a067f69536" />

<img width="2416" height="1084" alt="image" src="https://github.com/user-attachments/assets/d768063a-68a8-48b3-9e4c-74f072dd590c" />


### Client (Windows - Forwarder):
The second virtual machine, running Windows, acts as a forwarder. It sends multiple types of logs (Application, Security, System, and Setup) from the system to the Splunk server for processing and analysis.

<img width="1129" height="603" alt="image" src="https://github.com/user-attachments/assets/4831c1f4-acb3-4480-b654-f18a6db53d11" />


## Attack

After completing the SIEM setup, a third virtual machine was created to simulate a brute-force attack against the system acting as the forwarder. This scenario made it possible to observe real-time event ingestion on the server.



<img width="1436" height="202" alt="image" src="https://github.com/user-attachments/assets/1a7fce40-8035-446b-b5c6-022fe20a8452" />

As shown, a high volume of events is generated on the forwarder, corresponding to failed login attempts within a short period of approximately 5 minutes.

<img width="2429" height="250" alt="image" src="https://github.com/user-attachments/assets/a486024f-2b9c-477e-b178-4f06dedf6b34" />

<img width="1048" height="507" alt="image" src="https://github.com/user-attachments/assets/42e1d0a3-fcd2-49b3-8841-21590dc96c51" />

## Future Improvements
Future enhancements for this SIEM include:

- Implementing automated alerts for anomalous behavior.
- Developing correlation rules to identify attack patterns.
- Integrating mechanisms to infer potential malware types based on observed behavior.

These improvements aim to strengthen detection and response capabilities against security incidents.
