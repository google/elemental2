workspace(name = "com_google_elemental2")

load("@bazel_tools//tools/build_defs/repo:http.bzl", "http_archive")

# Load J2CL separately
_J2CL_VERSION = "master"

http_archive(
    name = "com_google_j2cl",
    strip_prefix = "j2cl-%s" % _J2CL_VERSION,
    url = "https://github.com/google/j2cl/archive/%s.zip" % _J2CL_VERSION,
)

load("@com_google_j2cl//build_defs:repository.bzl", "load_j2cl_repo_deps")

load_j2cl_repo_deps()

load("@com_google_j2cl//build_defs:workspace.bzl", "setup_j2cl_workspace")

setup_j2cl_workspace()

# Load other dependencies
load("//build_defs:repository.bzl", "load_elemental2_repo_deps")

load_elemental2_repo_deps()

load("//build_defs:workspace.bzl", "setup_elemental2_workspace")

setup_elemental2_workspace()
