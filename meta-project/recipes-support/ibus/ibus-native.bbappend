FILESEXTRAPATHS:prepend := "${THISDIR}/files:"

SRC_URI:append:scarthgap = " \
    file://0001-fix-load_emoji_dict-no-arg-call.patch \
"
