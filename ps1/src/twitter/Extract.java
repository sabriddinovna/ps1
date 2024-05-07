/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package twitter;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Extract consists of methods that extract information from a list of tweets.
 * 
 * DO NOT change the method signatures and specifications of these methods, but
 * you should implement their method bodies, and you may add new public or
 * private methods or classes if you like.
 */
public class Extract {

    /**
     * Get the time period spanned by tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return a minimum-length time interval that contains the timestamp of
     *         every tweet in the list.
     */
    public static Timespan getTimespan(List<Tweet> tweets) {

        if (tweets == null || tweets.isEmpty()) {
            throw new IllegalArgumentException("List of tweets cannot be null or empty");
        }

        Instant minTime = tweets.get(0).getTimestamp();
        Instant maxTime = tweets.get(0).getTimestamp();

        for (Tweet tweet : tweets) {
            Instant current = tweet.getTimestamp();
            if (current.isBefore(minTime)) {
                minTime = current;
            }
            if (current.isAfter(maxTime)) {
                maxTime = current;
            }
        }

        return new Timespan(minTime, maxTime);
    }



    /**
     * Get usernames mentioned in a list of tweets.
     * 
     * @param tweets
     *            list of tweets with distinct ids, not modified by this method.
     * @return the set of usernames who are mentioned in the text of the tweets.
     *         A username-mention is "@" followed by a Twitter username (as
     *         defined by Tweet.getAuthor()'s spec).
     *         The username-mention cannot be immediately preceded or followed by any
     *         character valid in a Twitter username.
     *         For this reason, an email address like bitdiddle@mit.edu does NOT 
     *         contain a mention of the username mit.
     *         Twitter usernames are case-insensitive, and the returned set may
     *         include a username at most once.
     */
    public static Set<String> getMentionedUsers(List<Tweet> tweets) {
        if (tweets == null || tweets.isEmpty()) {
            return new HashSet<>();
        }

        Set<String> mentionedUsers = new HashSet<>();
        // Twitter username pattern (ignores the case for usernames)
        String regex = "(?<=^|[^a-zA-Z0-9_])@([a-zA-Z0-9_]+)(?=$|[^a-zA-Z0-9_])";
        Pattern pattern = Pattern.compile(regex);

        for (Tweet tweet : tweets) {
            String text = tweet.getText();
            Matcher matcher = pattern.matcher(text);//matcher and pattern should be learnt

            while (matcher.find()) {
                mentionedUsers.add(matcher.group(1).toLowerCase()); // Add the username in lowercase
            }
        }

        return mentionedUsers;
    }
    }


