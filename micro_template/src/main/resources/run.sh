#!/usr/bin/env bash
NGINXD=nginx
$NGINXD -t
if [ $? = 0 ]; then
    echo "100"
    $NGINXD -s reload
    if [ $? = 0 ]; then
        echo "200"
        else
          $NGINXD
          if [ $? = 0 ]; then
             echo "300"
             else
                echo "-200"
          fi
    fi
    else
      echo "-100"
fi