#!/bin/bash

declare CPU_MASK
declare CORE_IDLE
declare POLLING_INTERVAL
declare NAS_HEAVY_TASK_NAME
declare -a HEAVY_TASKS_PID

CPU_MASK="0,1"
POLLING_INTERVAL=10
NAS_HEAVY_TASK_NAME="smbd"

function Fun_ShowBannerAndHelp() {
	echo -e " ***************************************************************************"
	echo -e " * CPU heavy load banlance assistant for LS1021A SAMBA NAS, Version 1.0"
	echo -e " * "
	echo -e " * Author: Xie Jianhua"
	echo -e " * Maintainer: Xie Jianhua"
	echo -e " * Modifier: Xie Jianhua <Jianhua.xie@nxp.com>"
	echo -e " * Copyright 2016 NXP Semiconductors N.V."
	echo -e " * "
	echo -e " * This program is free software; you can redistribute  it and/or modify it"
	echo -e " * under the terms of  the GNU General  Public License as published by the"
	echo -e " * Free Software Foundation;  either version 2 of the  License, or (at your"
	echo -e " * option) any later version."
	echo -e " * "
	echo -e " * Usage:\t$0 auto|restore"
	echo -e " * example:\t$0 auto"
	echo -e " * example:\t$0 restore"
	echo -e " * Note:\t$0 restore can restore all settings by this tool as the same as booting up."
	echo -e " * "
	echo -e " ***************************************************************************"
}

function Fun_IS_LS1021_SMP() {
	local IS_NXP_ARMv7_LS1021
	local CPU_CORES

	IS_NXP_ARMv7_LS1021=`cat /proc/cpuinfo |grep "Freescale LS1021A" |cut -d ":" -f2-`
	if [ "$IS_NXP_ARMv7_LS1021" != " Freescale LS1021A" ]; then
		echo This tool is only for LS1021A
		exit 1
	fi

	CPU_CORES=`cat /proc/cpuinfo |grep "ARMv7 Processor" |wc -l`
	if [ "$CPU_CORES" != "2" ]; then
		echo This tool is only for SMP 2 cores.
		exit 2
	fi
}

function Fun_LESS_IRQ_CORE() {
# Get IRQ Accumulation
	local  CPU0IRQS
	local  CPU1IRQS

	CPU0IRQS=`cat /proc/interrupts |sed -e "s/\s\+/ /g" |cut -d ":" -f2 |cut -d " " -f2|grep CPU -v |sort -n |awk '{sum += $1};END {print sum}'`
	CPU1IRQS=`cat /proc/interrupts |sed -e "s/\s\+/ /g" |cut -d ":" -f2 |cut -d " " -f3|grep CPU -v |sort -n |awk '{sum += $1};END {print sum}'`

	if [ $CPU0IRQS -lt $CPU1IRQS ]; then
		echo "0"
	else
		echo "1"
	fi
}

function Fun_Get_HEAVY_TASKS() {
	HEAVY_TASKS=`ps aux |grep $NAS_HEAVY_TASK_NAME |grep -v grep |sed -e "s/[[:space:]]\+/ /g" |cut -d " " -f2`
}

Fun_IS_LS1021_SMP

case $1 in
	auto | restore )
		while true; do
			CORE_IDLE=$(Fun_LESS_IRQ_CORE)
			Fun_Get_HEAVY_TASKS

			if [ "$1" == "auto" ]; then
				for i in ${HEAVY_TASKS[@]}; do
					taskset -pc $CORE_IDLE $i 2>&1 >> /tmp/$0.log
				done
			else
				for i in ${HEAVY_TASKS[@]}; do
				taskset -pc $CPU_MASK $i
				done
				exit 0
			fi
			sleep $POLLING_INTERVAL
		done
		;;
	*)
		Fun_ShowBannerAndHelp $*
		exit 4
		;;
esac
