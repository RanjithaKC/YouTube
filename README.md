YouTube Test Cases are as bellow :
testCase01: Go to YouTube.com and Assert you are on the correct URL. Click on "About" at the bottom of the sidebar, and print the message on the screen.

testCase02: Go to the "Films" or "Movies" tab and in the “Top Selling” section, scroll to the extreme right. Apply a Soft Assert on whether the movie is marked “A” for Mature or not. Apply a Soft assert on the movie category to check if it exists ex: "Comedy", "Animation", "Drama".

testCase03: Go to the "Music" tab and in the 1st section, scroll to the extreme right. Print the name of the playlist. Soft Assert on whether the number of tracks listed is less than or equal to 50.

testCase04: Go to the "News" tab and print the title and body of the 1st 3 “Latest News Posts” along with the sum of the number of likes on all 3 of them. No likes given means 0.

testCase05: Search for each of the items given in the stubs: src/test/resources/data.xlsx, and keep scrolling till the sum of each video’s views reach 10 Cr.
