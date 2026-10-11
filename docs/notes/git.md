GIT
```text
Your Computer                          Internet
┌─────────────────┐                  ┌──────────────────┐
│  library-app/   │                  │     GitHub       │
│  (Git tracks    │ ───── push ────▶ │  (stores a copy  │
│   the history)  │ ◀──── pull ───── │   of your repo)  │
└─────────────────┘                  └──────────────────┘

┌──────────────────────────────────────────┐
│ --system   (all users on this machine)   │
│  ┌────────────────────────────────────┐  │
│  │ --global   (all repos of YOU)      │  │
│  │  ┌──────────────────────────────┐  │  │
│  │  │ --local   (only this repo)   │  │  │
│  │  └──────────────────────────────┘  │  │
│  └────────────────────────────────────┘  │
└──────────────────────────────────────────┘
   Inner level overrides the outer level

user.name and user.email: The identity recorded on commits.
   git config --global user.name "Your-name"
   git config --global user.email "you@example.com"

init.defaultBranch main: Sets the default branch name for new projects to 'main'.
   git config --global init.defaultBranch main

core.editor: The editor Git opens to write commit messages (here, it's VS Code; change it if you use IntelliJ or something else).
   git config --global core.editor "code --wait"

git config --list
   user.name=Arsam Your-Last-Name
   user.email=you@example.com
   init.defaultbranch=main
   core.editor=code --wait
git config user.name

Your Computer                         GitHub
┌──────────────────┐              ┌───────────────────┐
│ id_ed25519       │              │ Your public key   │
│ (PRIVATE key)    │              │ (the "lock")      │
│ never share!     │              │                   │
│                  │              │                   │
│ id_ed25519.pub   │── you add ──▶│ stored in your    │
│ (PUBLIC key)     │   it once    │ account settings  │
└──────────────────┘              └───────────────────┘

When you push: GitHub checks that your private key
matches the public key. If yes, access is allowed.

ssh-keygen -t ed25519 -C "you@example.com"
   Generating public/private ed25519 key pair.
   Enter file in which to save the key (/home/arsam/.ssh/id_ed25519):
   Enter passphrase (empty for no passphrase):

cat ~/.ssh/id_ed25519.pub

testing → ssh -T git@github.com

Working Directory      Staging Area          Repository (.git)
(your files)           (the "next commit"    (saved history)
waiting room)
┌───────────────┐      ┌───────────────┐      ┌───────────────┐
│ Main.java     │ add  │ Main.java     │commit│ commit a1b2c3 │
│ Book.java     │─────▶│               │─────▶│ commit 9f8e7d │
│ (you edit     │      │ (you choose   │      │ (permanent    │
│  here)        │      │  what goes in)│      │  snapshots)   │
└───────────────┘      └───────────────┘      └───────────────┘

Untracked ──add──▶ Staged ──commit──▶ Unmodified (committed)
                     │
                     edit
                     ▼
                     Modified ──add──▶ Staged ...
                     
principal for writing a clean commit : 
Add Book class with title and author       <-- subject (max ~50 chars)
                                           <-- blank line
Book is a record so it is immutable.       <-- body (optional): WHY, not what
It will be used by the Library class.

git rm --cached Main.class

   edit files
       │
       ▼
  git status          <-- what changed?
       │
       ▼
  git diff            <-- what exactly changed?
       │
       ▼
  git add <files>     <-- choose what to commit
       │
       ▼
  git diff --staged   <-- final check
       │
       ▼
  git commit -m "..." <-- save snapshot
       │
       ▼
  git log --oneline   <-- verify
  
library-app/
├── .git/              <-- the whole repository lives here
│   ├── HEAD           <-- "where am I now?"
│   ├── index          <-- the staging area (a binary file)
│   ├── objects/       <-- ALL content: blobs, trees, commits
│   └── refs/
│       └── heads/
│           └── main   <-- the branch "main" (just a file!)
├── Main.java          <-- working directory
└── Book.java

 commit a1b2c3
 ┌────────────────────────┐
 │ tree      -------------┼──┐
 │ parent    (none)       │  │
 │ author    Arsam        │  │
 │ message   "Add Main"   │  │
 └────────────────────────┘  │
                             ▼
                      tree 7f8e9d  (root folder)
                      ┌──────────────────────┐
                      │ Main.java  -> blob 3c│
                      │ Book.java  -> blob 9a│
                      │ src/       -> tree 5b│
                      └──────────────────────┘
                              │
                              ▼
                      blob 3c  (content of Main.java)
                      
 a1b2c3 <---- c3d4e5 <---- f6a7b8
 (first)      (second)     (third)
   ^             ^            ^
   parent        parent       parent
   of c3d4e5     of f6a7b8    of nothing yet
   
        c3d4e5 ---- f6a7b8   (branch feature)
       /                  \
 a1b2c3                    m9n0p1  <-- merge commit (2 parents)
       \                  /
        d4e5f6 ---- b8c9d0   (branch main)
```