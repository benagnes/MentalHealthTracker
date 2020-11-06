## Git Workflow

### Getting Started

To get started, fork a copy of the master repository (top right corner). Branches with your changes in your fork are merged into the master branch in the master repository. Any releases or versions of the app are managed by creating tags.

Since we will be working with the upstream branch quite often, it's generally a good idea to add it as a remote.

```
// If you're using SSH:
git remote add upstream git@github.com:benagnes/MentalHealthTracker.git
// If you're using HTTPS:
git remote add upstream https://github.com/benagnes/MentalHealthTracker.git
```

### Keeping Your Fork In Sync With Upstream

It's quite common for the upstream repository to move ahead of your forked version (specifically the main repository's master branch and your forked repository's master branch). You'll need to make sure your master branches are in sync! Luckily this is fairly easy to do.

```
// Fetch the changes from the main repository
git fetch upstream
// Changed to your master branch
git checkout master
// Merge YOUR master branch with the MAIN master branch
git merge upstream/master
// Push your newly synced branch back up to GitHub
git push origin
```

### Adding Feature / Fixing Issue

- First pick an issue from the [issue list](https://github.com/benagnes/MentalHealthTracker/issues)
- Make sure your fork is updated! Fetch all the changes, and merge them into your fork if needed. (see previous section)
- Head over to Android Studio, open a terminal and create a new branch to fix your issue

```
// To keep things simple, when you work on an issue create a branch named after that issue. This branch is temporary and will be deleted after we're done with it. A branch exists solely to do a bit of work, and merge it in! Let's try not to maintain a crazy large history on each branch.

// If you're working on Issue#99, name the branch iss99

git checkout -b iss#
```

- Switch to the new branch you created `git checkout iss#x`
- Make some changes, and commit them. Be careful if using `git add .` (git add all) as you may be staging files that were not meant for this commit.
- Add any relevant test cases, and test your changes.
- Push your changes to your repository `git push origin`
- Make a pull request (ask to merge your created branch into the master branch). Note some of the changes you made, this will make it easier for the people reviewing your changes.
- Request someone to review your code if you have a non-trivial commit! It doesn't take very long in most cases, and it's better than having 1 person look at the code.

### Merging Commit

- To merge the commit, checkout the remote branch with the changes, and test it locally.

```
// The name of the remote can be upstream, or a name of any other remote you added for a separate fork
git checkout -b test <name of remote>/test
```

- If there are any merge conflicts, first checkout the master branch of the upstream repository. In Android Studio, in the top menu `VCS > Git > Merge Changes` and select the branch you're looking to resolve conflicts with from the list. Use the merge viewer to pick and choose what should be kept in the commit. Commit all your changes with `git add .` and push them to the upstream master branch!

### Testing Procedures

Whenever you finish a feature or make a bug fix it's good practice to test what impact your changes had on the repo. Consider running the following commands locally:

- `./gradlew lint`
- `./gradlew test`
- `./gradlew build`

These are the same commands that the CI pipeline runs. (On Windows you may need to run the .bat file). Note the output files. Any new errors/warnings from the linter should be discussed with the team to assess the impact.
