#! /bin/sh
tar -zcvPf /opt/backupmongo/db_`hostname -i`_apiprod_`date +%F`.tar.gz  /grdata/tenant/79da0e7d35bf460bb77298518bb8d21e/service/048bc0e273a44c22840f17dcfec191f7/bitnami/mongodb/data/db
find /opt/backupmongo -type f -name '*tar.gz' -mtime +10|xargs rm -rf