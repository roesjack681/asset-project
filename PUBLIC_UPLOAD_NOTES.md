# Public source snapshot

This repository contains the current local project source. Previous local Git history is not included.

Local secrets, environment files, databases, runtime logs, dependency directories and compiled application binaries are excluded. Hardcoded credentials in the enterprise and asset projects have been replaced with environment variables. Configure your own services before running.

The backend is in `traffic-ops-platform/`; the frontend is in `traffic-ops-vue3/`. Generated reports containing business data are excluded. Frontend local environment settings must be recreated.

Binary resources are stored with Git LFS. Install Git LFS before cloning, or run `git lfs install` and `git lfs pull` after cloning. The initial import uses multiple small commits for reliable transfer.
