import socket

# Define a dictionary of possible requests and responses
responses = {
    "takeoff": "ok",
    "battery?": "50",
    "command": "ok"
}

# Create a UDP socket and bind it to a port
server_socket = socket.socket(socket.AF_INET, socket.SOCK_DGRAM)
server_address = ('', 8000)
server_socket.bind(server_address)

print('UDP server started on port', server_address[1])

# Continuously receive and handle incoming requests
while True:
    # Receive a request from a client
    data, client_address = server_socket.recvfrom(1024)
    request = data.decode()
    print('[R] %s' % request)
    # Look up the corresponding response in the dictionary
    if request in responses:
        response = responses[request]
    else:
        response = "Unknown request."
    print('[S] %s' % response)
    # Send the response back to the client
    server_socket.sendto(response.encode(), client_address)
