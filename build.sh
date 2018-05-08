#!/usr/bin/env bash

#Builds the scala app
sbt compile

#Installs the client side dependencies
npm install

#Builds the client app using webpack
npm run build