# meta-project/classes/serialize-task-compile.bbclass

python __anonymous () {
    """ serialize specific PN to reduce parallel task resource useage """

    serialized_prefixes = [
        "boost",
        "cargo",
        "clang",
        "gcc",
        "linux-intel",
        "linux-jammy-nvidia",
        "linux-raspberrypi",
        "linux-yocto",
        "llvm",
        "mozjs",
        "ovmf",
        "rust",
        "webkitgtk",
    ]

    pn = d.getVar("PN") or ""

    if any(pn.startswith(prefix) for prefix in serialized_prefixes):

        # too agressive
        # d.setVar("PARALLEL_MAKE:task-compile", "-j 1")

        cur = d.getVarFlag("do_compile", "lockfiles") or ""
        cur += (" %s/compile.lock" % d.getVar("TOPDIR"))
        d.setVarFlag("do_compile", "lockfiles", cur)

        cur = d.getVarFlag("do_install", "lockfiles") or ""
        cur += (" %s/install.lock" % d.getVar("TOPDIR"))
        d.setVarFlag("do_install", "lockfiles", cur)
}
