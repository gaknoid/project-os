ARG UBUNTU_VERSION=24.04
FROM ubuntu:$UBUNTU_VERSION

ARG USER_NAME=builds
ARG USER_UID=1001
ARG USER_GID=1001

ENV DEBIAN_FRONTEND=noninteractive

RUN apt-get update && apt-get install -y --no-install-recommends apt-utils \
    && apt-get install -y --no-install-recommends \
    bash build-essential ca-certificates chrpath cpio curl debianutils diffstat \
    file gawk git iputils-ping lz4 locales python3 python3-git python3-jinja2 \
    python3-pexpect python3-pip python3-subunit socat sudo texinfo unzip wget xterm xz-utils zstd

RUN locale-gen en_US.UTF-8 && update-locale LANG=en_US.UTF-8 LC_ALL=en_US.UTF-8

ENV LANG=en_US.UTF-8
ENV LC_ALL=en_US.UTF-8
ENV LANGUAGE=en_US:en

RUN curl -fsSL https://storage.googleapis.com/git-repo-downloads/repo -o /usr/local/bin/repo \
    && chmod 0755 /usr/local/bin/repo

COPY .github/workflows/build-container-image.sh /usr/local/bin/entrypoint.sh
RUN chmod 0755 /usr/local/bin/entrypoint.sh

RUN groupadd --gid $USER_GID $USER_NAME \
    && useradd --uid $USER_UID --gid $USER_GID --create-home --shell /bin/bash $USER_NAME \
    && echo "$USER_NAME ALL = (ALL:ALL) NOPASSWD: ALL" > /etc/sudoers.d/$USER_NAME

USER builds

ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]
CMD ["/bin/bash"]
