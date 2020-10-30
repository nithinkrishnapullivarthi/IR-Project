package com.ir.proj;

public class Test {

    public static void main(String[] args){
        String punctuationStrippedString = "Correction published May appended to this article\n" +
                "FRANK it flies shouted someone at Sir Frank Whittle during the maiden\n" +
                "flight of a British jet Of course it does replied Sir Frank who\n" +
                "patented the first aircraft gas turbine Thats what it was bloody well\n" +
                "designed to do wasnt it\n" +
                "Exactly years ago yesterday the first British jet made a brief 17minute\n" +
                "flight from RAF Cranwell in Lincolnshire To celebrate the event Mr Eric\n" +
                "Winkle Brown a test pilot of the prototype Gloster Whittle\n" +
                "jet Mr Geoffrey Bone a engineer and Mr Charles McClure a\n" +
                "75yearold pilot returned to RAF Cranwell They are seen in front of a\n" +
                "restored Meteor NF Sir Frank was unable to attend because of illhealth\n" +
                "The Gloster Whittle was not the first jet to fly a Heinkel had its\n" +
                "maiden flight in August months before the British aircraft\n" +
                "Correction published May 1991\n" +
                "THE PICTURE of a Gloster Whittle jet on Page of the issue of Tuesday May\n" +
                "14 was taken at Bournemouth Airport and not at RAF Cranwell as stated in\n" +
                "the caption";
        //String out = s.replaceAll("[a-zA-Z]*\\d+[a-zA-Z]*", "");
        //System.out.println(punctuationStrippedString.matches(".*\\d.*"));

        StringBuilder sb = new StringBuilder();
        for(String word : punctuationStrippedString.split("\\W+")){
            if(!word.matches(".*\\d.*")) {
                sb.append(word).append(" ");
            }
        }
        System.out.println(sb.toString());
    }
}
