#!/bin/bash
read  -p "please input your net want to check (for example:192.168.100):" ip
for i in {1..254};do                    ##ִ��forѭ����䣬ȡֵ��ΧΪ1-254
  ping -c 2 -I eth0 $ip$i &>/dev/null     ##��дѭ���壬ʹ��ping�����������������ͨ��
  if [ $? -eq 0 ];then                    ##ʹ��if�ж���䣬�ж�ping����Ľ��������������ִ�гɹ�
    B=$(arping -I eth0 -f $ip$i |grep Unicast |awk '{print $5}')    ##������ping����ִ�гɹ���֤�����������ڣ��������BΪ��������MAC��ַ
	echo "$ip$i is starting MAC=$B"         ##��������������MAC��ַ����B����Ļ
  else                                    ##��ping����ִ�в��ɹ���֤������������
	echo "$ip$i is stoping"
  fi
done