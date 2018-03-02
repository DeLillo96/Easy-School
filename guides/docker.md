###Project Scripts
>`./up.sh`  
>Accende e se non sono presenti crea i container di docker definiti nel file di configurazione docker-compose.yml.

>`./down.sh`  
>Spegne i container di docker se presenti.

###Commands
>`docker ps`  
>Visualizza i container docker accesi.

>`docker rm <container name or id>`  
>Rimuove il container con il nome o l'id indicato.

>`docker exec -it <container name or id> <command>`  
>Esegue il <command> nel container con il nome o l'id specificato.  
>L'opzione -it serve per potersi interfacciare con il comando lanciato.

_Per ulteriori informazioni e approfondimenti guardare il manuale utente del servizio docker._