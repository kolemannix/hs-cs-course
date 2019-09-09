package solutions

import scala.util.Random

import lib.Util._

object CaesarCipher extends App {

  def alphabet = "abcdefghijklmnopqrstuvwxyz"
  lazy val scrambledAlphabet = alphabet.sortBy(_ => Random.nextBoolean())

  def letterPosition(l: Char): Int = {
    alphabet.indexOf(l)
  }

  def encryptLetter(letter: Char, offset: Int): Char = {
    val position = letterPosition(letter)
    val shifted = (position + offset) % alphabet.length
    alphabet.charAt(shifted)
  }

  def decryptLetter(letter: Char, offset: Int): Char = {
    val position = letterPosition(letter)
    val shifted = (position - offset) % alphabet.length
    val shiftedAgain = if (shifted < 0) shifted + alphabet.length else shifted
    alphabet.charAt(shiftedAgain)
  }

  def caesarEncrypt(message: String, offset: Int): String = {
    require(offset > 0, "Offset must be greater than zero!")

    val encryptedMessage = message.toLowerCase.map { char =>
      if (!char.isLetter) {
        char
      } else {
        encryptLetter(char, offset)
      }
    }
    encryptedMessage
  }

  def caesarDecrypt(message: String, offset: Int): String = {
    require(offset > 0, "Offset must be greater than zero!")

    val decryptedMessage = message.toLowerCase.map { char =>
      if (!char.isLetter) {
        char
      } else {
        decryptLetter(char, offset)
      }
    }
    decryptedMessage
  }

  example {
    val sample = "enemy has 5 howitzers"
    println("Sample: " + sample)
    val encrypted = caesarEncrypt(sample, 3)
    println("\tEncrypted: " + encrypted)
    val decrypted = caesarDecrypt(encrypted, 3)
    println("\tDecrypted: " + decrypted)
  }

  example {
    // Does it work with a scrambled alphabet?
    val sample = "i am 9 years old"
    println("Sample: " + sample)
    val encrypted = caesarEncrypt(sample, 3)
    println("\tEncrypted: " + encrypted)
    val decrypted = caesarDecrypt(encrypted, 3)
    println("\tDecrypted: " + decrypted)

  }

  def caesarEncryptedecrypt(message: String, offset: Int): String = {
    val mappedCharacters = message.toLowerCase().map { char =>
      if (!char.isLetter) {
        char
      } else {
        val position = letterPosition(char)
        val shifted = (position + offset) % alphabet.length
        val newPos = if (shifted < 0) {
          shifted + alphabet.length
        } else {
          shifted
        }
        val newChar = alphabet.charAt(newPos)
        newChar
      }
    }
    mappedCharacters
  }
}

object VigenereCipher extends App {

  example {
    val secretMessage = "hojr dgdzv trf hewpr gvakw avo zyg wamlgrh ccsjoyt jqrih zr byil eoctjyicb, t pel olxxwe, gqnrejgis zn pkbtruj, ied hgdxcbeis ko xje psztdaztbsp ihbe ptc fip prf ggmrtxh efubw. vfw ag prf icorgxh ic b kgmrt gkvxl heg, txwvicg hltbyek vhpt yeiqfn, qr aoj cikihr sd dzrrmzvxh acd ds lvdbgcttd, gpv lhri tnefvt. wx crt npx we t irtau fpbklx-hitle su khtx wpr. at yaoi cdmf xd uewmeaie l ewitbsp df elpb fbind, bd p wigen getemco peeee fpc ipfsx yhd ipvt xaoi twejc aqmel vhpt elpb ntxkoc ntkwb lbzg. iu mh rlmsieihfc uqktbri pne tgwgek vhpt hi ayonpf so elxa. uyv, io e trrzit heodi, ne gcn npe smuiveve -- at tag poi dzrhmtrtxg -- hi krn rqt hbwpde -- vhxs rvdced. vht ccekm mxr, aiwtrv rnw fepd, aww smvwgvlfo wmie, jake nscavckeves je, nrr edoke zyg gohv pdwfc iw awh og epxgitt. vht xzval wbpn aiuept eomi, cos pdvx kioebbfc lprt ag haz ltzv, fwt iu gpv nxzgr fpcktb waev ihfj squ aite. je xa fhv uh usi tzvbri, rbeltz, ms bt ephxkrtxh htrf xd khx wnuiotwwmu pstk witgw khxc wwo qsjoyt lgrt ilzt khnw fpr ds vfbec asvbygtl. bx ih slxwmi yst js es jv aite dfomrikew vo tip vzvam vahk cibiznbri qegzvt ls -- ihbe uzfm xjehe sscwiew fepd hi brkx knrrflwtl dxzqtxoo xd khtx cputp uwi plkcw usin xaoi twe wehb fnpn bebdygm oy fekoutsc -- xjai xp wmie lkgwlz vtafloi twau xwmje hgas tseat nhx hpvf hxmu br vpio -- khtx twit rpbzog, ucdfc vwu, wjaal sekm a rgw bjcxw ff jtetdpx -- agh twau kddvrgqgni pq ipv iiqpae, fn khx redpmp, nfr xje pfztam, llcla ozx xvrbwj urpx ipv xettw."
    val secretKey = "capablepirate"

  }
}

