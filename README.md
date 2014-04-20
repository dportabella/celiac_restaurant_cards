Celiac Restaurant Cards
=======================

From [http://www.celiactravel.com/cards/](http://www.celiactravel.com/cards/)

> Get safe gluten free food wherever you are.

> These Celiac Travel.com gluten free restaurant cards are for you to print out and take with you when you dine out at a restaurant, either at home or abroad. The great thing about being able to print your own is you can give them to the waiter or waitress to take to the kitchen. The most common problem with getting gluten free food when dining in restaurants is that your message gets lost or corrupted on its way to the kitchen.

[celiactravel.com](http://www.celiactravel.com/cards/) provides these cards in 54 languages, in a A4 page size.

## Credit-card size
This project reshapes the cards from celiactravel.com into a credit-card size so that you can bring them in your wallet. This repository contains the source code to generate the cards in PDF format. However the source text files of the cards are not include here, except for three examples: English, Catalan and Turkish.

I'll send the generated PDF cards to celiactravel.com, and they might make them available in their website: http://www.celiactravel.com/cards/

## Run
Install [sbt](http://www.scala-sbt.org/). See also below 'Getting the libraries of apache-FOP'.

To generate all cards:

    $ sbt run

To generate only the English and Catalan cards:

    $ sbt "run english catalan"


The PDF cards will be created at target/credit_card_layout/*.pdf


## How it works
This program uses [XSL Formatting Objects](http://en.wikipedia.org/wiki/XSL_Formatting_Objects), a markup language for document formatting, and the [Apache-FOP implementation](http://xmlgraphics.apache.org/fop/) which can render a XSL-FO document to a PDF document. I separate the text of the card (the content) from the layout, so that the card could be automatically formatted in different formats (A4, credit-card size, with or without logo...). However, I do not use XSLT to generate the XSL-FO document (I have already spent too much time implementing workarounds for bypassing the limitations of XSLT). Instead, I implemented a simple [Scala](http://scala-lang.org) program that builds a XSL-FO document with a given credit-card size layout, using the text of the card stored in a simple xml format (it's much easier to create/manipulate xml documents in Scala rather than in XSLT). Here there is an example of the steps involved, for the English card:

input file: src/main/resources/english.xml

    <?xml version="1.0" encoding="utf-8"?>
    <card>
      <p1>I have an illness called Celiac Disease and have to follow a strict gluten free diet.</p1>
      <p2>I may therefore become very ill if I eat food containing flours or grains of wheat, rye, barley and oats.</p2>
      <p3>Does this food contain flour or grains of wheat, rye, barley or oats? If you are at all uncertain about what the food contains, please tell me.</p3>
      <p4>I can eat food containing rice, maize, potatoes, all kinds of vegetables and fruit, eggs, cheese, milk, meat and fish - <strong>as long as they are not cooked with wheat flour, batter, breadcrumbs or sauce</strong>.</p4>
      <p5>Thank you for your help.</p5>
    </card>

generated XSL-FO file: target/credit_card_layout/english.fo

    <fo:root xmlns:fox="http://xmlgraphics.apache.org/fop/extensions" xmlns:fo="http://www.w3.org/1999/XSL/Format">
      <fo:layout-master-set>
        <fo:simple-page-master master-name="simple" page-height="5.4cm" page-width="8.5cm" margin-top="0pt" margin-bottom="0pt" margin-left="2pt" margin-right="2pt">
          <fo:region-body margin-top="0cm"/>
          <fo:region-before extent="0cm"/>
          <fo:region-after extent="0cm"/>
        </fo:simple-page-master>
      </fo:layout-master-set>
      <fo:page-sequence master-reference="simple">
        <fo:flow flow-name="xsl-region-body">
          <fo:block border-width="2pt" border-style="solid" border-color="#829660" fox:border-radius="10pt">
            <fo:block font-size="9.0pt" font-family="Arial" font-weight="bold" text-align="center" space-after="0.20cm">
                English Gluten Free Restaurant Card
              </fo:block>
            <fo:block font-size="7.5pt" font-family="Arial" text-align="center" space-after="0.20cm">
                I have an illness called Celiac Disease and have to follow a strict gluten free diet.
              </fo:block>
            <fo:block font-weight="bold" font-size="7.5pt" font-family="Arial" text-align="center" space-after="0.20cm">
                I may therefore become very ill if I eat food containing flours or grains of wheat, rye, barley and oats.
              </fo:block>
            <fo:block font-size="7.5pt" font-family="Arial" text-align="center" space-after="0.20cm">
                Does this food contain flour or grains of wheat, rye, barley or oats? If you are at all uncertain about what the food contains, please tell me.
              </fo:block>
            <fo:block font-size="7.5pt" font-family="Arial" text-align="center" space-after="0.38cm">
                I can eat food containing rice, maize, potatoes, all kinds of vegetables and fruit, eggs, cheese, milk, meat and fish - <fo:inline font-weight="bold">as long as they are not cooked with wheat flour, batter, breadcrumbs or sauce</fo:inline>.
              </fo:block>
            <fo:block font-weight="bold" font-size="7.5pt" font-family="Arial" text-align="center" space-after="0.10cm">
                Thank you for your help.
              </fo:block>
            <fo:block font-size="7.5pt" font-family="Arial" text-align="center" space-after="0.10cm">
                © Copyright celiactravel.com
              </fo:block>
          </fo:block>
        </fo:flow>
      </fo:page-sequence>
    </fo:root>
    
generated XSL-FO file: target/credit_card_layout/english.pdf

TODO: add download link


## Getting the libraries of apache-FOP
I use the ["rounded corners" feature](https://xmlgraphics.apache.org/fop/trunk/extensions.html) of Apache-FOP. Unfortunately this feature is currently not released, and it is available only in their repository trunk. So you need to build the project yourself and copy the libraries to the celiac_restaurant_cards project. I hope this inconvenient will be fixed soon.

    $ svn co http://svn.apache.org/repos/asf/xmlgraphics/fop/trunk fop-trunk
    $ cd fop-trunk
    $ ant all
    $ mkdir ~/celiac_restaurant_cards/lib
    $ cp build/*.jar ~/celiac_restaurant_cards/lib
    $ cp lib/*.jar ~/celiac_restaurant_cards/lib



## Acknowledgments
Thanks to [celiactravel.com](http://www.celiactravel.com/) for sharing these celiac restaurant cards in 54 languages; this makes my life easier. You are awesome!


## Disclamer
Use this project at Your Own Risk. I am no affiliated with celiactravel.com, although I have their permission to publish the three card examples here.

