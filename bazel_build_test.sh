#!/bin/bash
# Script that can be used by CI server for testing elemental2 builds.
set -e

bazel build ...
