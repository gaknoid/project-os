# Upstream wpa_supplicant make install uses install -D for multiple binaries, which can race under parallel make install.
PARALLEL_MAKEINST = ""