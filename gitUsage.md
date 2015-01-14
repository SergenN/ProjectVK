Haal repo binnen met de SSH Clone URL, als het goed is staan je remotes meteen goed.
```bash
$ git clone git@github.com:IcyPalm/Vossen-en-Konijnen.git
```

Staan je remotes niet goed, dan doe je ook nog:
```bash
$ git remote add origin git@github.com:IcyPalm/Vossen-en-Konijnen.git
```

Maak voordat je ergens aan werkt, Ã©Ã©rst even een branch aan! Dan hoeven we geen rekening te houden met wat voor aanpassingen anderen doen. Via command line gaat dat zo:
```bash
# eerst even de meest recente master van github halen:
$ git checkout master
$ git pull
# nu een nieuwe branch aanmaken, gebaseerd op master:
$ git branch mijn-epische-feature
$ git checkout mijn-epische-feature
Switched to branch 'mijn-epische-feature'
```

Als je dan dingen aanpast, kun je pushen naar Github met:
```bash
$ git push origin mijn-epische-feature
```
En dan via github mergen als je denkt dat het ook daadwerkelijk een epische feature is, anders kun je eerst even een Pull Request openen zodat anderen het kunnen nakijken.
