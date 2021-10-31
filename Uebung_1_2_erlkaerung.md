# Aufgabenblatt 1
### 1.1 Netzwerk-Traces mit Wireshark
- _siehe .pcap files_
- Beantwortung der Fragen:
    - #### Aufgabe 1a:
        * Beobachtete Protokolle:
            * DNS - Layer 5-7
            * UDP - Layer 4
            * TCP - Layer 4
            * TLSv1.2 - Layer 5

    - #### Aufgabe 1b:
        * Die IP-Adresse mit der Google antwortet lauter 142.250.186.36

        * Beobachtete Protokolle:
            * DNS - Layer 5-7
            * ICMP - Layer 3

    - #### Aufgabe 1c:
        Der Linux File Manager meldet sich mit dem Benutzernamen anonymous und dem 
        Passwort gvfsd-ftp-1.44.1@example.com an

        * Beobachtete Protokolle:
            * FTP - Layer 5-7
            * FTP-DATA - Layer 5-7
            * TCP - Layer 4
            * NTP - Layer 7
            * MDNS - Layer 3
            * DNS - Layer 5-7

    - Der Niedrigste Layer Taucht Zuerst auf, bspw. so:
``` 
Layer 1
Layer 2
Layer 3
... usw
```


### 1.2 Netzwerkkommunikation – ganz ohne Netzwerkprogrammierung
- Beispiel 1:
    - <u>Server</u>:
    `nc -k -l 4711`: Server mit geöffnetem Port 4711. `-k` zwingt `nc`weiterhin nach einer verbindung zu suchen (nur in Kombination mit `-l`). `-l` sucht nach eingehenden Verbindungen.
    - <u>Client</u>:
    `nc localhost 4711`: Client baut eine Verbindung zum Server mit der ip=`localhost` auf und verbindet sich mit dessen Port 4711. 
- Beispiel 2:
    - <u>Server</u>:
    `nc -l 4711 > received.txt`: Server empfängt Text über Port 4711 und schreibt in in die Datei `received.txt`. Diese Datei wird, falls noch nicht vorhanden, automatisch erstellt.
    - <u>Client</u>: 
    `nc localhost 4711 < test.txt` Verbindung zu `localhost`sendet den Inhalt von `test.txt` an Port `4711` 
- Beispiel 3:
    - <u>Vorbereitung</u>: `mkfifo /tmp/testPipe` erstellt Pipe am angebenen Ort. 
    - <u>Server</u>: `cat /tmp/testPipe | nc -l 4711 | sed -u "s/Vorlesung/Disko/g" > /tmp/testPipe`. `cat` (copy at terminal) gibt den Text am Terminal aus, der dann gepiped wird zur `nc`-Anweisung. `sed` sucht im erhaltenen Text nach "Vorlesung" und ersetzt ihn durch "Disko". Der überarbeitete Text wird anschließend wieder in die Pipe geschrieben.  
    - <u>Client</u>: `nc localhost 4711` _siehe oben_