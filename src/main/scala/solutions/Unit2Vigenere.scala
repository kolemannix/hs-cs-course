package solutions

object VigenereCipher extends App {
  def vigenereEncrypt(message: String, key: String): String = {
    require(key.forall(_.isLetter), "Vigenere key must all letters")
    message.toLowerCase.zipWithIndex.map { case (char, i) =>
      if (!char.isLetter) {
        char
      } else {
        val keyIndex = i % key.length
        val keyChar = key.charAt(keyIndex)
        val keyOffset = CaesarCipher.letterPosition(keyChar)
        val encrypted = CaesarCipher.encryptLetter(char, keyOffset)
        encrypted
      }
    }.mkString
  }

  def vigenereDecrypt(message: String, key: String): String = {
    require(key.forall(_.isLetter), "Vigenere key must all letters")
    message.toLowerCase.zipWithIndex.map { case (char, i) =>
      if (!char.isLetter) {
        char
      } else {
        val keyIndex = i % key.length
        val keyChar = key.charAt(keyIndex)
        val keyOffset = CaesarCipher.letterPosition(keyChar)
        val decrypted = CaesarCipher.decryptLetter(char, keyOffset)
        decrypted
      }
    }.mkString
  }

  {
    val sample = "enemy has 5 howitzers"
    println("Sample: " + sample)
    val encrypted = vigenereEncrypt(sample, "supersecret")
    println("\tEncrypted: " + encrypted)
    val decrypted = vigenereDecrypt(encrypted, "supersecret")
    println("\tDecrypted: " + decrypted)
  }

  {
    val secretMessage = "hojr dgdzv trf hewpr gvakw avo zyg wamlgrh ccsjoyt jqrih zr byil eoctjyicb, t pel olxxwe, gqnrejgis zn pkbtruj, ied hgdxcbeis ko xje psztdaztbsp ihbe ptc fip prf ggmrtxh efubw. vfw ag prf icorgxh ic b kgmrt gkvxl heg, txwvicg hltbyek vhpt yeiqfn, qr aoj cikihr sd dzrrmzvxh acd ds lvdbgcttd, gpv lhri tnefvt. wx crt npx we t irtau fpbklx-hitle su khtx wpr. at yaoi cdmf xd uewmeaie l ewitbsp df elpb fbind, bd p wigen getemco peeee fpc ipfsx yhd ipvt xaoi twejc aqmel vhpt elpb ntxkoc ntkwb lbzg. iu mh rlmsieihfc uqktbri pne tgwgek vhpt hi ayonpf so elxa. uyv, io e trrzit heodi, ne gcn npe smuiveve -- at tag poi dzrhmtrtxg -- hi krn rqt hbwpde -- vhxs rvdced. vht ccekm mxr, aiwtrv rnw fepd, aww smvwgvlfo wmie, jake nscavckeves je, nrr edoke zyg gohv pdwfc iw awh og epxgitt. vht xzval wbpn aiuept eomi, cos pdvx kioebbfc lprt ag haz ltzv, fwt iu gpv nxzgr fpcktb waev ihfj squ aite. je xa fhv uh usi tzvbri, rbeltz, ms bt ephxkrtxh htrf xd khx wnuiotwwmu pstk witgw khxc wwo qsjoyt lgrt ilzt khnw fpr ds vfbec asvbygtl. bx ih slxwmi yst js es jv aite dfomrikew vo tip vzvam vahk cibiznbri qegzvt ls -- ihbe uzfm xjehe sscwiew fepd hi brkx knrrflwtl dxzqtxoo xd khtx cputp uwi plkcw usin xaoi twe wehb fnpn bebdygm oy fekoutsc -- xjai xp wmie lkgwlz vtafloi twau xwmje hgas tseat nhx hpvf hxmu br vpio -- khtx twit rpbzog, ucdfc vwu, wjaal sekm a rgw bjcxw ff jtetdpx -- agh twau kddvrgqgni pq ipv iiqpae, fn khx redpmp, nfr xje pfztam, llcla ozx xvrbwj urpx ipv xettw."
    val secretKey = "capablepirate"
    println(vigenereDecrypt(secretMessage, secretKey))
  }

}

object VigenereApp extends App {

  while (true) {
    print("Message: ")
    val input = scala.io.StdIn.readLine()
    print("Secret Key: ")
    val key = readLine()

    val output = VigenereCipher.vigenereEncrypt(input, key)
    println("Encrypted: " + output)
  }
}

