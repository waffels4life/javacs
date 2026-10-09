GIT

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