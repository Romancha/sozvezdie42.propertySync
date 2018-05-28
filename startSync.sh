#!/bin/bash
java -jar /opt/soz42sync/iproperty-sync-1.0-SNAPSHOT.jar > /opt/soz42sync/logs/sync_$(date +%d-%m-%y-%H:%M).log &