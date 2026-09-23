An app for practicing usage of Room library and Navigation UI from UpskillMe_Android Course.
This practice task is aimed at creating a note-taking application. 

Used it to practice aider architect mode.
All the agent's models are gemini-flash-latest.
ProjectStructure.md, StructuredTaskDescription.md, UnitTests.md prewritten by Gemini Pro 3.1.

Project creation and aider launch executed with .sh scripts.
Only files required by aider copied manually between project creation and aider generation launch:
    .aider.conf.yml,
    .aider.conventions.md,
    aider_run.sh,
    StructuredTaskDescription.md,
    ProjectStructure.md,
    UtitTests.md.

The code is pretty clean and structured.
Test are not full, because of wrong prompt and UtitTests.md file.

This project has version 2 label because of broken pipeline of 1st generation.
What was wrong?
I'd changed UI framework convention from XML Navigation to Jetpack Compose manually after generating all the explanation files.
Aider fairly tried generate Navigation UI structure with Jetpack Compose elements, what led to overpopulated structure and impossibility to build the project.
After realizing the problem, requested to clean the project from Navigation code, keeping Compose.
All the process was executed on gemini-pro-latest version of LLM, so I've spent x12 of usual cost for project generation.
