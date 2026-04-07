# Configurations and variables for glymur SoC family.

SOC_FAMILY = "shikra"
require conf/machine/include/qcom-base.inc
require conf/machine/include/qcom-common.inc

require conf/machine/include/arm/arch-armv8-1a.inc

MACHINE_ESSENTIAL_EXTRA_RRECOMMENDS += " \
    packagegroup-qcom-boot-essential \
"

MACHINE_EXTRA_RRECOMMENDS += " \
    packagegroup-qcom-boot-additional \
"
