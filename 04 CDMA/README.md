# To implement a basic function of Code Division Multiple Access (CDMA) to test the orthogonality and autocorrelation of a code to be used for CDMA operation. Write an application based on the above concept. 


### What is CDMA?

* CDMA stands for Code Division Multiple Access.
* It is a digital cellular standard that utilizes spread-Spectrum Technology.
* It spreads the signal over a fully available spectrum or over multiple channels through division.
* It is a more secure and private line.
* It has good voice and data communication capabilities.
* The information is sent simultaneously through several transmitters over a single communication channel.

---

### Procedure:

1.  The station encodes its data bit as follows.
         If bit = 1 then +1
         If bit = 0 then  -1

    no signal(interpreted as 0) if station is idle

2.  Each station is allocated a different orthogonal sequence (code) which is N bit long for N stations

3.  Each station does a scalar multiplication of its encoded data bit and code sequence.

4.  The resulting sequence is then stored on the channel.

5.   Since the channel is common, amplitudes add up and hence resultant channel sequence is the sum of sequences from all channels.

6.  If station 1 wants to listen to station 2, it multiplies (inner product) the channel sequence with code of station S2.

7.  The inner product is then divided by N to get data bit transmitted from station 2.

---
Credits
<https://www.theprogrammingcodeswarehouse.com/2020/04/implementation-of-basic-function-of.html>
---

------
$$  $$