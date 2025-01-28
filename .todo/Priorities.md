# Priorities

1. Finish filling Flow pitfalls
2. Start integrating code into the slides.
    1. Check how animations work
3. Add illustrations for basics
    1. Edit the DSL to support custom Composables or images
4. Add new dispositions:
    1. "Big answer": packed + centered
5. Titles
   1. Remove body and put everything in the title when is SlideContent.SingleElement (and rename it to TitlesOnly or something)
   2. Ensure subtitles are shown
   3. Animate for startCentered https://stackoverflow.com/a/70031663
   4. Display parentTitles within fewer space
6. Text
    1. Support formatting (create a custom Text composable)
        1. Try getting inspiration from ResumeComposition to convert to AnnotatedString or CuP stuff.
        2. Ensure we have emojis work on WASM.
    2. Change the style based on the depth and disposition
    3. Add some color
7. Debug spacing by putting a unique background color before padding and after spacer's height