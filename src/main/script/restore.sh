#!/bin/bash


# dl le targ gz

# decompresser

#  sauvegarder ancien /ngdb

# copier le contenu du tag gz dans /ngdb

# importer le sql

sudo rm -rf ngdb
sudo tar zxf *.tar.gz
sudo rm -rf /ngdb_bak
sudo cp -rf /ngdb /ngdb_bak
sudo rm -rf /ngdb
sudo mv ngdb/ /
mysql -uroot -pm1710calos ngdb < sql/2012_08_27.sql

