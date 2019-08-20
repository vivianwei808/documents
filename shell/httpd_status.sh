#!/bin/bash
if [ $1 == start ];then
	netstat -utpln |grep 80 &>/dev/null
	if [ $? -eq 0 ];then
		echo "httpd is started!"
		exit 1
	else
	/usr/local/httpd/bin/apachectl start &>/dev/null
	echo "+/usr/local/httpd/bin/apachectl start"
	echo "+netstat -utpln |grep 80"	
	netstat -utpln |grep 80
	PID=$(netstat -utpln |grep 80 |awk '{print $7}'|awk -F'/' '{print $1}')
	echo -e "httpd is running ! PID is $PID"
	fi
elif [ $1 == stop ];then
	netstat -utpln |grep 80 &>/dev/null
	if [ $? -ne 0 ];then
		echo "httpd is stoped!"
		exit 1
	else
	/usr/local/httpd/bin/apachectl stop &>/dev/null
	echo "+/usr/local/httpd/bin/apachectl stop"
	sleep 2
	echo "+netstat -utpln |grep 80"
	netstat -utpln |grep 80
		if [ $? -ne 0 ];then
			echo "httpd is stopping!"
		fi
	fi
elif [ $1 == restart ];then
	netstat -utpln |grep 80 &>/dev/null
	if [ $? -ne 0 ];then
		echo "httpd not is started!"
		/usr/local/httpd/bin/apachectl start &>/dev/null
        	echo "+/usr/local/httpd/bin/apachectl start"
        	sleep 2
        	echo "+netstat -utpln |grep 80"
        	netstat -utpln |grep 80
        	if [ $? -eq 0 ];then
                	echo "httpd is started!"
        	fi
	else
	/usr/local/httpd/bin/apachectl stop &>/dev/null
        echo "+/usr/local/httpd/bin/apachectl stop"
	sleep 2
	/usr/local/httpd/bin/apachectl start &>/dev/null
        echo "+/usr/local/httpd/bin/apachectl start"
	sleep 2
        echo "+netstat -utpln |grep 80"
        netstat -utpln |grep 80
        	if [ $? -eq 0 ];then
                	echo "httpd is started!"
        	fi
	fi
elif [ $1 == status ];then
	netstat -utpln |grep 80 &>/dev/null
	if [ $? -eq 0 ];then
		echo "httpd is running!"
		PID=$(netstat -utpln |grep 80 |awk '{print $7}'|awk -F'/' '{print $1}')
        	echo -e "httpd is running ! PID is $PID"
	else
		echo "httpd is not running!"
	fi
else
	echo "Usage: /etc/init.d/httpd start|stop|restart|status "
fi