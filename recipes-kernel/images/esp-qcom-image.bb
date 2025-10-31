DESCRIPTION = "EFI System Partition Image to boot Qualcomm boards"

PACKAGE_INSTALL = " \
    systemd-boot \
"

KERNELDEPMODDEPEND = ""
KERNEL_DEPLOY_DEPEND = ""

ESPFOLDER = ""
inherit image uki uki-esp-image features_check

UKI_FILENAME = "${EFI_LINUX_IMG}"

# UKI_CMDLINE = "root=${QCOM_BOOTIMG_ROOTFS} rw rootwait console=${KERNEL_CONSOLE}"
# UKI_CMDLINE = "root=/dev/nvme0n1p17 rw rootwait console=${KERNEL_CONSOLE}"
# UKI_CMDLINE += "${@d.getVar('KERNEL_CMDLINE_EXTRA') or ''}"

UKI_CMDLINE = "shell console=ttyMSM0 earlycon=qcom_geni,0x894000 nokaslr root=/dev/nvme0n1p17 rw rootwait initrd=/init no_console_suspend=1 ignore_loglevel clk_ignore_unused pd_ignore_unused fw_devlink=off swiotlb=0 lpm_levels.sleep_disabled=1 rcupdate.rcu_cpu_stall_suppress_at_boot=1 printk.devkmsg=on console_msg_format=syslog loglevel=8 kpti=0"

# Remove 'upstream' dtb, rely on EFI provided one
KERNEL_DEVICETREE = "${QCOM_DTB_DEFAULT}.dtb"


IMAGE_FSTYPES = "vfat"
IMAGE_FSTYPES_DEBUGFS = ""

EXTRA_IMAGECMD:vfat += " -S ${QCOM_VFAT_SECTOR_SIZE}"

# Align image size with the expected partition size (512MB)
IMAGE_ROOTFS_SIZE = "524288"
IMAGE_ROOTFS_MAXSIZE = "524288"
IMAGE_ROOTFS_EXTRA_SPACE = "0"

IMAGE_LINGUAS = ""
IMAGE_FEATURES = ""

remove_unused_files() {
    find ${IMAGE_ROOTFS} -mindepth 1 ! -path "${IMAGE_ROOTFS}/EFI*" -exec rm -rf {} +
}
IMAGE_PREPROCESS_COMMAND:append = " remove_unused_files"

do_uki[vardeps] += "KERNEL_CMDLINE_EXTRA"

# ESP image is currently only used on EFI machines
REQUIRED_MACHINE_FEATURES = "efi"
