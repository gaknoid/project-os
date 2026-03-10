# Allow for MACHINEOVERRIDES to be extended
# export BB_ENV_PASSTHROUGH_ADDITIONS="$BB_ENV_PASSTHROUGH_ADDITIONS EXTRA_MACHINEOVERRIDES"
# export EXTRA_MACHINEOVERRIDES=":<extended>"
MACHINEOVERRIDES .= "${@':%s' d.getVar('EXTRA_MACHINEOVERRIDES' if d.getVar('EXTRA_MACHINEOVERRIDES') else ''}"

# Append project-wide machine features to all machines that include this file.
# These supplement any features already declared by the machine BSP layer.
#
#   alsa          - ALSA sound subsystem support
#   bluetooth     - Bluetooth hardware and stack support
#   keyboard      - Keyboard input device support
#   pci           - PCI bus support
#   qemu-usermode - QEMU user-mode emulation (enables cross-arch package execution in build)
#   rtc           - Real-time clock hardware support
#   screen        - Display/framebuffer output support
#   serial        - Serial port support
#   usbgadget     - USB device/gadget mode support
#   usbhost       - USB host controller support
#   vfat          - FAT/VFAT filesystem support (required for EFI ESP partition)
#   wifi          - Wi-Fi support
# Append project-wide machine features to all machines that include this file.
# These supplement any features already declared by the machine BSP layer.
#
#   alsa          - ALSA sound subsystem support
#   bluetooth     - Bluetooth hardware and stack support
#   keyboard      - Keyboard input device support
#   pci           - PCI bus support
#   qemu-usermode - QEMU user-mode emulation (enables cross-arch package execution in builds)
#   rtc           - Real-time clock hardware support
#   screen        - Display/framebuffer output support
#   serial        - Serial port support
#   usbgadget     - USB device/gadget mode support
#   usbhost       - USB host controller support
#   vfat          - FAT/VFAT filesystem support (required for EFI ESP partition)
#   wifi          - Wi-Fi support
MACHINE_FEATURES:append = " alsa bluetooth keyboard pci qemu-usermode rtc screen serial usbgadget usbhost vfat wifi"
MACHINE_FEATURES:remove = ""

PROVIDES:pn-grub-efi += "virtual/bootloader"
HOMEPAGE:pn-tpm2-tools ?= "upstream value not provided"
SUMMARY:pn-python3-tpm2-pytss ?= "upstream value not provided"

# ---------------------------------------------------------------------------
# MACHINE intel-core2-32
# ---------------------------------------------------------------------------

MACHINE_FEATURES:append:intel-core2-32 = " efi tpm"
EFI_PROVIDER:intel-core2-32 = "grub-efi"
PREFERRED_PROVIDER_virtual/bootloader:intel-core2-32 = "grub-efi"
PREFERRED_PROVIDER_virtual/kernel:intel-core2-32 = "linux-yocto"
SOTA_MACHINE:intel-core2-32 = "none"

# ---------------------------------------------------------------------------
# MACHINE intel-corei7-64
# ---------------------------------------------------------------------------

MACHINE_FEATURES:append:intel-corei7-64 = " efi tpm2"
EFI_PROVIDER:intel-corei7-64 = "grub-efi"
PREFERRED_PROVIDER_virtual/bootloader:intel-corei7-64 = "grub-efi"
PREFERRED_PROVIDER_virtual/kernel:intel-corei7-64 = "linux-yocto"
SOTA_MACHINE:intel-corei7-64 = "none"

# ---------------------------------------------------------------------------
# MACHINE qemux86-64
# ---------------------------------------------------------------------------

MACHINE_FEATURES:append:qemux86-64 = " efi tpm2"
EFI_PROVIDER:qemux86-64 = "grub-efi"
PREFERRED_PROVIDER_virtual/bootloader:qemux86-64 = "grub-efi"
PREFERRED_PROVIDER_virtual/kernel:qemux86-64 = "linux-yocto"
SOTA_MACHINE:qemux86-64 = "none"

# ---------------------------------------------------------------------------
# MACHINE qemuarm
# ---------------------------------------------------------------------------

MACHINE_FEATURES:append:qemuarm = ""
EFI_PROVIDER::qemuarm = "grub-efi"
PREFERRED_PROVIDER_virtual/bootloader:qemuarm = "u-boot"
PREFERRED_PROVIDER_virtual/kernel:qemuarm = "linux-yocto"
SOTA_MACHINE:qemuarm64 = "none"

# ---------------------------------------------------------------------------
# MACHINE qemuarm64
# ---------------------------------------------------------------------------

MACHINE_FEATURES:append:qemuarm64 = " efi"
EFI_PROVIDER::qemuarm64 = "grub-efi"
PREFERRED_PROVIDER_virtual/bootloader:qemuarm = "u-boot"
PREFERRED_PROVIDER_virtual/kernel:qemuarm64 = "linux-yocto"
SOTA_MACHINE:qemuarm64 = "none"

# ---------------------------------------------------------------------------
# MACHINE jetson-orin-nano-devkit
# ---------------------------------------------------------------------------

MACHINE_FEATURES:append:jetson-orin-nano-devkit = " efi"
PREFERRED_PROVIDER_virtual/kernel:jetson-orin-nano-devkit = "linux-yocto"
SOTA_MACHINE:jetson-orin-nano-devkit = "none"

# ---------------------------------------------------------------------------
# MACHINE jetson-orin-nano-devkit-nvme
# ---------------------------------------------------------------------------

MACHINE_FEATURES:append:jetson-orin-nano-devkit-nvme = " efi"
PREFERRED_PROVIDER_virtual/kernel:jetson-orin-nano-devkit-nvme = "linux-yocto"
SOTA_MACHINE:jetson-orin-nano-devkit-nvme = "none"

# ---------------------------------------------------------------------------
# MACHINE raspberrypi5
# ---------------------------------------------------------------------------

MACHINE_FEATURES:append:raspberrypi5 = " vc4graphics"
PREFERRED_PROVIDER_virtual/kernel:raspberrypi5 = "linux-yocto"
SOTA_MACHINE:raspberrypi5 = "none"
