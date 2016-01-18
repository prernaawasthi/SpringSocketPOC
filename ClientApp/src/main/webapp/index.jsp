<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Client</title>
<script type="text/javascript" src="scripts/jquery-ui.min.js"></script>
<script type="text/javascript" src="scripts/jquery.js"></script>
<script type="text/javascript" src="scripts/sockjs-0.3.4.js"></script>
<script type="text/javascript" src="scripts/stomp.js"></script>
<link rel="stylesheet" href="styles/jquery-ui.css" type="text/css" />
</head>
<script type="text/javascript">
	var stompClient = null;

	function setConnected(connected) {
		document.getElementById('connect').disabled = connected;
		document.getElementById('disconnect').disabled = !connected;
		document.getElementById('conversationDiv').style.visibility = connected ? 'visible'
				: 'hidden';
		document.getElementById('response').innerHTML = '';
	}

	function connect() {
		var socket = new SockJS('http://localhost:8080/hello');
		stompClient = Stomp.over(socket);
		stompClient.connect({'Access-Control-Allow-Origin':'*'}, function(frame) {
			setConnected(true);
			console.log('Connected: ' + frame);
			stompClient.subscribe('/topic/greetings', function(greeting) {
				showGreeting(JSON.parse(greeting.body).content);
			});
		});
	}

	function disconnect() {
		if (stompClient != null) {
			stompClient.disconnect();
		}
		setConnected(false);
		console.log("Disconnected");
	}

	function sendName() {
		var name = document.getElementById('name').value;
		stompClient.send("/app/hello", {'Access-Control-Allow-Origin':'*'}, JSON.stringify({
			'name' : name
		}));
	}

	function showGreeting(message) {
		var response = document.getElementById('response');
		var p = document.createElement('p');
		p.style.wordWrap = 'break-word';
		p.appendChild(document.createTextNode(message));
		response.appendChild(p);
	}
</script>
</head>
<body onload="disconnect()">
	<noscript>
		<h2 style="color: #ff0000">Seems your browser doesn't support
			Javascript! Websocket relies on Javascript being enabled. Please
			enable Javascript and reload this page!</h2>
	</noscript>
	<div>
		<div>
			<button id="connect" onclick="connect();">Connect</button>
			<button id="disconnect" disabled="disabled" onclick="disconnect();">Disconnect</button>
		</div>
		<div id="conversationDiv">
			<label>What is your name?</label><input type="text" id="name" />
			<button id="sendName" onclick="sendName();">Send</button>
			<p id="response"></p>
		</div>
	</div>



	<div align="center" id="response"></div>

	<input type="button" value="list" onclick="submitorder();" />


	<table cellpadding="5" cellspacing="5"
		style="border: 1px solid #ccc; background: #F1F6F6">
		<thead>
			<tr>
				<th>Serial No</th>
				<th>User Name</th>
				<th>Date</th>
			</tr>
		</thead>
		<tbody id="tbody"></tbody>
	</table>
</html>