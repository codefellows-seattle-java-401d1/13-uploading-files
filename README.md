Create a web server where users can upload `.txt` files. Process the texts
and show the user the text's readability score. Use the Flesch-Kincaid metric
to calculate the score.

You may assume the end of sentence is denoted by any token whose last character
is not a letter.

Approximate syllable counting by following the following rules:
* any word five characters or less counts as one syllable
* all other words divide their length by 2 and round down.

![Flesch-Kincaid formula](./grade-level.svg)

## Testing
* Write small unit tests against known scored small lengths of text.
* Write unit tests that make sure your program parses sentences in text
  correctly when there are exclamation marks and question marks present.
* Download large texts from Project Gutenberg and write a test that
  processes a large text.
* Choose several texts and write tests to prove your program can score
  texts with low readability, average readability and high scores.
