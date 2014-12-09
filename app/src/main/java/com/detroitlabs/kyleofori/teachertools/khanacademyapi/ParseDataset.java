package com.detroitlabs.kyleofori.teachertools.khanacademyapi;

import com.parse.ParseObject;
import com.parse.SaveCallback;

/**
 * Created by kyleofori on 12/3/14.
 */
public class ParseDataset {
    public void prepopulateParseDataset() {
        ParseObject lessonPlan0 = new ParseObject("LessonPlan");
        lessonPlan0.put("author", "Tish Raff");
        lessonPlan0.put("title", "The Civil Rights Movement");
        lessonPlan0.put("description", "Students will understand the following: 1. Beyond the famous leaders of the Civil Rights Movement, ordinary men and women struggled for their beliefs. 2. All the participants—famous and not so famous—deserve to have their stories told. 3. Older people have a responsibility to pass on these stories to younger people.");
        lessonPlan0.put("subject", "U.S. History");
        lessonPlan0.put("lessonUrl", "http://www.discoveryeducation.com/teachers/free-lesson-plans/the-civil-rights-movement.cfm");
        lessonPlan0.put("hostingSite", "Discovery Education");
        lessonPlan0.put("gradeLevels", "6-8");
        lessonPlan0.saveInBackground();

        ParseObject lessonPlan1 = new ParseObject("LessonPlan");
        lessonPlan1.put("author", "Discovery Education");
        lessonPlan1.put("title", "Rational Number Concepts");
        lessonPlan1.put("description", "Students will: Understand Egyptian achievements in mathematics. Understand how Egyptians used hieroglyphics to write numerals. Multiply and divide numbers using the Egyptian doubling and addition method. Write fractions as Egyptian fractions.");
        lessonPlan1.put("subject", "Math");
        lessonPlan1.put("lessonUrl", "http://www.discoveryeducation.com/teachers/free-lesson-plans/rational-number-concepts.cfm");
        lessonPlan1.put("hostingSite", "Discovery Education");
        lessonPlan1.put("gradeLevels", "9-12");
        lessonPlan1.saveInBackground();

        ParseObject lessonPlan2 = new ParseObject("LessonPlan");
        lessonPlan2.put("author", "21st Century Lessons");
        lessonPlan2.put("title", "Order of Operations Day 1 (of 2)");
        lessonPlan2.put("description", "This is the first lesson of a unit series on the order of operations. This lesson is an exploration around the order of operations with practice. The goal for this lesson is for students to understand that evaluating math expressions has a particular order. As the unit progresses, students will be asked to apply the order of operations, therefore, fitting to be introduced at this time. Lesson Objective: Students will explore the same expression in four different ways to help determine the order of operations. Aligned with Common Core Standards: 6.EE.1 , 6.EE.2c");
        lessonPlan2.put("subject", "Math");
        lessonPlan2.put("lessonUrl", "http://www.sharemylesson.com/teaching-resource/order-of-operations-day-1-of-2-50009300/");
        lessonPlan2.put("hostingSite", "Share My Lesson");
        lessonPlan2.put("gradeLevels", "6");
        lessonPlan2.saveInBackground();

//        ParseObject lessonPlan3 = new ParseObject("LessonPlan");
//        lessonPlan3.put("author", "Science Team");
//        lessonPlan3.put("title", "Covalent Bonds handout");
//        lessonPlan3.put("description", "I've designed this handout as part of a one- lesson introduction to covalent bonding for my 8th grade class. It consists of a fill the gap followed by questions asking the students to draw dot and cross diagrams for various molecules and then compound");
//        lessonPlan3.put("subject", "Chemistry");
//        lessonPlan3.put("lessonUrl", "http://www.sharemylesson.com/teaching-resource/covalent-bonds-handout-6082208/");
//        lessonPlan3.put("hostingSite", "Share My Lesson");
//        lessonPlan3.put("gradeLevels", "9-12");
//        lessonPlan3.saveInBackground();
//
//        ParseObject lessonPlan4 = new ParseObject("LessonPlan");
//        lessonPlan4.put("author", "Teaching Tolerance");
//        lessonPlan4.put("title", "Using Photographs to Teach Social Justice");
//        lessonPlan4.put("description", "Photographs can sometimes capture important moments in American history. Activities will help students: analyze the time period of a photograph to gain a greater understanding of history; explore issues of racism, stereotypes, and bias; explore how photographs can expose racism. Aligned to Common Core State Standard: RH.9-10.1");
//        lessonPlan4.put("subject", "English Language Arts");
//        lessonPlan4.put("lessonUrl", "http://www.sharemylesson.com/teaching-resource/covalent-bonds-handout-6082208/");
//        lessonPlan4.put("hostingSite", "Share My Lesson");
//        lessonPlan4.put("gradeLevels", "9-12");
//        lessonPlan4.saveInBackground();
//
//        ParseObject lessonPlan5 = new ParseObject("LessonPlan");
//        lessonPlan5.put("author", "Adam Feinberg");
//        lessonPlan5.put("title", "Booker T. Washington vs. WEB DuBois and Rappers");
//        lessonPlan5.put("description", "This lesson/activity has students look at Booker T. Washington's advice to blacks after Reconstruction ended and compare it to W.E.B. Dubois's advice. It also uses the music of rap artists Talib Kweli and Lauryn Hill to see how views on black empowerment have changed today.");
//        lessonPlan5.put("subject", "Social Studies & History");
//        lessonPlan5.put("lessonUrl", "http://www.sharemylesson.com/teaching-resource/booker-t-washington-vs-web-dubois-and-rappers-50008908/");
//        lessonPlan5.put("hostingSite", "Share My Lesson");
//        lessonPlan5.put("gradeLevels", "11");
//        lessonPlan5.saveInBackground();
//
//        ParseObject lessonPlan6 = new ParseObject("LessonPlan");
//        lessonPlan6.put("author", "Lesson Planet");
//        lessonPlan6.put("title", "A Lesson Before Dying: Symbols");
//        lessonPlan6.put("description", "Explore the religious symbolism in A Lesson Before Dying by Ernest J. Gaines. Once your class has read through Chapter 21, lead them in a discussion about the way religious symbols make their way through the novel, and their impact on the characters and plot. They then examine how various character names could act as symbols, and how each character represents his or her symbolic namesake. Pass out the handout on Jackie Robinson and Joe Louis for homework to discuss Grant's concept of a hero.");
//        lessonPlan6.put("subject", "Social Studies & History");
//        lessonPlan6.put("lessonUrl", "http://www.lessonplanet.com/teachers/a-lesson-before-dying-symbols");
//        lessonPlan6.put("hostingSite", "Lesson Planet");
//        lessonPlan6.put("gradeLevels", "9-12");
//        lessonPlan6.saveInBackground();
//
//        ParseObject lessonPlan7 = new ParseObject("LessonPlan");
//        lessonPlan7.put("author", "Lesson Planet");
//        lessonPlan7.put("title", "Summer Journal Ideas");
//        lessonPlan7.put("description", "Twenty prompts, fifteen starters, and ten situations. What more could you ask for from a list of journal ideas?");
//        lessonPlan7.put("subject", "English Language Arts");
//        lessonPlan7.put("lessonUrl", "http://www.lessonplanet.com/teachers/summer-journal-ideas");
//        lessonPlan7.put("hostingSite", "Lesson Planet");
//        lessonPlan7.put("gradeLevels", "2-12");
//        lessonPlan7.saveInBackground();
//
//        ParseObject lessonPlan8 = new ParseObject("LessonPlan");
//        lessonPlan8.put("author", "Lesson Planet");
//        lessonPlan8.put("title", "Ain’t I a Woman?");
//        lessonPlan8.put("description", "Learners discover the impact of women on civil rights in United States history by analyzing primary source clues to identify influential female figures.");
//        lessonPlan8.put("subject", "Social Studies & History");
//        lessonPlan8.put("lessonUrl", "http://www.lessonplanet.com/teachers/aint-i-a-woman");
//        lessonPlan8.put("hostingSite", "Lesson Planet");
//        lessonPlan8.put("gradeLevels", "5-8");
//        lessonPlan8.saveInBackground();
//
//        ParseObject lessonPlan9 = new ParseObject("LessonPlan");
//        lessonPlan9.put("author", "Lesson Planet");
//        lessonPlan9.put("title", "True Blue? On Being Australian");
//        lessonPlan9.put("description", "Who or what is an Australian? Discover a plethora of student-centered, engaging activity ideas on the question of Australian identity, organized according to five major themes: people, symbols, place, sport, and words.");
//        lessonPlan9.put("subject", "Social Studies & History");
//        lessonPlan9.put("lessonUrl", "http://www.lessonplanet.com/teachers/true-blue-on-being-australian");
//        lessonPlan9.put("hostingSite", "Lesson Planet");
//        lessonPlan9.put("gradeLevels", "11-12");
//        lessonPlan9.saveInBackground();

    }

}