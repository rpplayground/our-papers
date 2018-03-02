#!/bin/sh

# $Id: export-text.sh 3658 2007-10-15 16:29:11Z schapira $
# Part of the open-source Proximity system (see LICENSE for copyright
# and license information).

# Runs Proximity application, by setting the appropriate classpath

# Get the bin directory
bindir=`echo "$0" | sed 's/export-text.sh$//'`
. "${bindir}/classpath.sh"

java -classpath $classpath kdl.prox.app.ExportTextApp $*