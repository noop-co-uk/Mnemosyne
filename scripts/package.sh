#!/bin/bash
./scripts/verify.sh
mvn source:jar javadoc:jar package
