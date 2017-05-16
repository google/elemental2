# Description:
#  utilities for Elemental2 Build.


# Patch extern file.
def patch_extern_file(name, src, patch_file):
  patched_file_name = "%s.js" % name

  native.genrule(
      name = name,
      srcs = [src, patch_file],
      outs = [patched_file_name],
      # GNU patch doesn't follow symbolic links and we need to use the options --follow_symlinks
      # the original patch command doesn't know that option but handle correctly symbolic links
      cmd = (
          "[[ $$(patch --version) == \"GNU patch\"* ]] && extra_args='--follow-symlinks' || extra_args='';" +
          "patch $${extra_args} $(location %s) -i $(location %s) -o $@ " % (src, patch_file)

      ),

      visibility = ["//:__subpackages__"],
  )
