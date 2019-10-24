<h3>How to run</h3>
<ol>
	<li>make sure, your mysql server is up</li>
	<li>create database bersling in your mysql server</li>
	<li>replace database.password in resources/app.properties according to your mysql server</li>
</ol>

<ol>
	<h4>Building Instruction</h4>
	Do the following steps
	<li>Open Your terminal</li>
	<li><strong>git clone 'https://github.com/UbaidurRehman1/JavaCore19.git'</strong></li>
	<li><strong>cd JavaCore19/bersling</strong></li>
	<li><strong>mvn test clean compile assembly:single</strong></li>
	<li><strong>mv /target/bersling_1-jar-with-dependencies.jar ./ </strong></li>
	<li><strong>java -jar bersling_1-jar-with-dependencies.jar</strong> to run the jar</li>
</ol>