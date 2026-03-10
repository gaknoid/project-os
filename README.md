# project-os

Yocto Project workspace for the `scarthgap` release, with a project-owned layer (`meta-project`), upstream layers managed by a local repo manifest, and container/CI automation for reproducible builds.

## Repository Layout

- `manifests/`: Repo manifests used to fetch/pin upstream layers.
- `layers/meta-project`: Project-owned layer (images, packagegroups, templates, docs).
- `layers/poky`: Upstream Poky checkout.
- `layers/meta-openembedded`: Upstream OpenEmbedded checkout.
- `layers/meta-security`: Upstream security layer checkout.
- `layers/meta-virtualization`: Upstream virtualization layer checkout.
- `layers/meta-updater`: Uptane/OTA layer checkout.
- `layers/meta-clang`: Clang/LLVM layer checkout.
- `layers/meta-intel`: Intel BSP layer checkout.
- `layers/meta-raspberrypi`: Raspberry Pi BSP layer checkout.
- `layers/meta-tegra`: NVIDIA Tegra BSP layer checkout.
- `.github/workflows/`: Build and container CI workflows.
- `.vscode/tasks.json`: Common Yocto tasks for local development.

## Build Templates

Templates live in `layers/meta-project/conf/templates/`:

- `machine-intel-corei7-64`
- `machine-qemux86-64`
- `machine-qemuarm64`
- `machine-raspberrypi5`
- `machine-jetson-orin-nano-devkit-nvme`

Each template seeds `build/conf/local.conf` and `build/conf/bblayers.conf` for that target.

## Common BitBake Targets

- `core-image-project` (project image from `meta-project`)
- `core-image-project-sdk` (project image from `meta-project`)
- `core-image-minimal`
- `core-image-full-cmdline`

## Quick Start (Host Build)

### 1) Install required host tools

At minimum, make sure `curl`, `git`, and `lz4` are available. On Ubuntu:

```bash
sudo apt-get update
sudo apt-get install -y build-essential chrpath cpio debianutils diffstat file gawk gcc git \
  iputils-ping libacl1 libcrypt-dev locales python3 python3-git python3-jinja2 python3-pexpect \
  python3-pip python3-subunit socat texinfo unzip wget xz-utils zstd
sudo apt-get install -y curl git lz4 docker.io ripgrep
```

For self-hosted GitHub Actions runners, the runner manages job containers itself and needs a Docker API socket available at `/var/run/docker.sock`.

### 2) Install the Google repo launcher

```bash
mkdir -p "$HOME/.local/bin"
curl -fsSL https://storage.googleapis.com/git-repo-downloads/repo -o "$HOME/.local/bin/repo"
chmod +x "$HOME/.local/bin/repo"
export PATH="$HOME/.local/bin:$PATH"
```

### 3) Sync layers from manifest

```bash
cd <project-root>
repo init -u . -m manifests/default.xml
repo sync -j"$(nproc)"
```

### 4) Initialize the build directory

Available templates:
- `machine-intel-corei7-64`
- `machine-qemux86-64`
- `machine-qemuarm64`
- `machine-raspberrypi5`
- `machine-jetson-orin-nano-devkit-nvme`

```bash
cd <project-root>
rm -rf build/conf
TEMPLATECONF=../../layers/meta-project/conf/templates/machine-qemux86-64 \
  source layers/poky/oe-init-build-env build
```

### 5) Build an image

```bash
bitbake core-image-project
```

Alternative targets based on default settings:

```bash
bitbake core-image-minimal
bitbake core-image-full-cmdline
```

## Container-Based Build

The Ubuntu build container images are published to `ghcr.io/gaknoid/project-os` and are intended to be pulled from there rather than built locally as part of normal setup.
These images pin a known Ubuntu userspace for the selected Poky release so the build environment stays compatible even when the host OS is a different or more general Linux installation.

Published tags include:

```text
ghcr.io/gaknoid/project-os/ubuntu-20.04:latest
ghcr.io/gaknoid/project-os/ubuntu-22.04:latest
ghcr.io/gaknoid/project-os/ubuntu-24.04:latest
```

The GitHub Actions workflows and any local container wrappers should consume these published images from `ghcr.io`.

## VS Code Tasks

The workspace ships with ready-to-run tasks in `.vscode/tasks.json`:

- `project-os: Init and Sync Layers`

## GitHub Actions Workflows

Current workflows under `.github/workflows/`:

- `build-core-image-project.yml`:
  manual build of `core-image-project` with selectable template.
- `build-core-image-minimal.yml`:
  manual build of `core-image-minimal` with selectable template.
- `build-core-image-full-cmdline.yml`:
  manual build of `core-image-full-cmdline` with selectable template.
- `build-container-image.yml`:
  self-hosted workflow to build and push Ubuntu 20.04/22.04/24.04 container images to GHCR.

Image build workflows run on `self-hosted` runners and use reusable local actions:

- `.github/actions/repo-sync`
- `.github/actions/bitbake-build`
- `.github/actions/deploy-release`

> **A self-hosted runner is required for all image build workflows.** GitHub-hosted runners do not have sufficient disk space for the Yocto downloads and sstate-cache directories, and the 6-hour job time limit is insufficient for a cold Yocto build. The `sstate-cache` retained on the self-hosted runner is what makes incremental builds practical.

### Self-Hosted Runner Host Setup With Docker

The image-build workflows use job-level `container:` definitions. On a self-hosted runner, GitHub Actions creates those containers through `/usr/bin/docker` and expects a Docker-compatible API socket at `/var/run/docker.sock`.
Those job containers are used to supply a Poky-compatible Ubuntu version for the workflow while letting the self-hosted runner host remain on a broader host OS baseline.

Install Docker Engine and ensure the Actions runner service account can access `/var/run/docker.sock`.
If Docker is managed by systemd, add the runner user to the `docker` group and restart the runner service after group membership changes.

Prepare the runner directory:

```bash
sudo mkdir -p /srv/actions-runner/runner1
sudo chown -R builds:builds /srv/actions-runner
```

Reload and restart the runner service after any Docker configuration updates:

```bash
sudo systemctl daemon-reload
sudo systemctl restart actions.runner.gaknoid-project-os.runner1.service
```

Verify the runner user can reach the Docker API:

```bash
sudo -iu builds docker version
sudo -iu builds docker ps
```

If the runner service uses a different account, replace `builds` accordingly.

Composite action script convention:

- Use `action.sh` as the shell entrypoint name for new `.github/actions/*` composite actions.

## Running QEMU Images

For QEMU templates, run images with `runqemu` after a successful build.

```bash
runqemu qemux86-64 core-image-project nographic slirp
```

For `qemuarm64` template:

```bash
runqemu qemuarm64 core-image-project nographic slirp
```

Deploy artifacts are under:

```text
build/tmp/deploy/images/<machine>/
```

## Optional: Local PR Service (prserv)

Use a local PR server to keep package revision increments consistent.

Start:

```bash
mkdir -p build/cache
bitbake-prserv --start \
  --host 127.0.0.1 \
  --port 8585 \
  --file build/cache/prserv.sqlite3 \
  --log build/cache/prserv.log
```

Enable in `build/conf/local.conf`:

```conf
PRSERV_HOST = "127.0.0.1:8585"
```

Stop:

```bash
bitbake-prserv --stop --host 127.0.0.1 --port 8585
```

## Optional: Sync Yocto Caches to S3

If you use shared caches, sync `build/downloads` and `build/sstate-cache` with `s3cmd`.

```bash
s3cmd sync build/downloads/ s3://<bucket>/<prefix>/downloads/
s3cmd sync build/sstate-cache/ s3://<bucket>/<prefix>/sstate-cache/
```

Use `--dry-run` first to review changes safely.
