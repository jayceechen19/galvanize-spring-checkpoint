package com.example.galvanizespringcheckpoint;

import java.util.*;

import org.springframework.web.bind.annotation.*;

@RestController
public class TextService {
    @GetMapping("/camelize")
    public String camelize(@RequestParam(required=true)String original,
                           @RequestParam(required=false, defaultValue = "false")Boolean initialCap){
        String str = "";
        for(int i=0; i<original.length();i++){
            String preceding;
            if(i > 0){
                preceding= String.valueOf(original.charAt(i-1));
            }else{
                preceding = "";
            }

            String letter = String.valueOf(original.charAt(i));

            if(preceding.equals("_") || (i ==0 && initialCap)){
                str = str.concat(letter.toUpperCase());
            }else if(!letter.equals("_")){
                str = str.concat(letter);
            }
        }

        return str;
    }
    @GetMapping("/redact")
    public String redact(@RequestParam(required=true)String original,
                         @RequestParam(required=false, defaultValue = "")String... badWord) {

        for(String word:badWord){
            int length = word.length();
            String stars = new String(new char[length]).replace("\0", "*");
            original = original.replaceAll(word, stars);
        }

        return original;
    }
    @PostMapping("/encode")
    public String encode(@RequestParam("message") String message,
                         @RequestParam("key") String key){

        Map<String, String> encoder = new HashMap<> ();
        String alphabet = "abcdefghijklmnopqrstuvwzyz";

        for(int i=0; i<alphabet.length(); i++){
            String alphaChar = String.valueOf(alphabet.charAt(i));
            String keyChar = String.valueOf(key.charAt(i));
            encoder.put(alphaChar,keyChar);
        }

        String str = "";
        System.out.println(message);
        for(int i=0; i<message.length(); i++) {
            String letter = String.valueOf(message.charAt(i));
            if (letter.equals(" ")) {
                str = str.concat(" ");
            } else {
                if (encoder.containsKey(letter)) {
                    str = str.concat(encoder.get(letter));
                }
            }

        }
        return str;
    }

    @PostMapping("/s/{find}/{replacement}")
    public String sed(@PathVariable String find, @PathVariable String replacement,
                      @RequestBody String body
    ){
        return body.replaceAll(find,replacement);
    }


}
