pdf(file='C:/Users/Fatemeh/Downloads/Validation-Nov2015/Validation/NovData/SepELDvsFD.pdf') 
hs <- read.csv(file='C:/Users/Fatemeh/Downloads/Validation-Nov2015/Validation/NovData/SepELDvsFD.csv', head=TRUE, sep="," )
c<-rep(0,280)
c2<-rep(1,280)
plot(hs$ELDLogLikelihood,c, pch=16, col=ifelse(  hs$class <2, 'blue','red'),main="High Correlation Synthetic dataset", xlab='ELD, FD*20',ylab='',yaxt="n") 
text(15,-0.1,"ELD distribution")
points(hs$ELDPrior*20,c2, pch=16, col=ifelse( hs$class <2, 'blue','red'), xlab='ELD, FD*20',ylab='',xaxt="n") 
text(4,0.9,"FD distribution")
dev.off()

pdf(file='C:/Users/Fatemeh/Downloads/Validation-Nov2015/Validation/NovData/StrikersELDvsFD.pdf') 
hs <- read.csv(file='C:/Users/Fatemeh/Downloads/Validation-Nov2015/Validation/NovData/StrikersELDvsFD.csv', head=TRUE, sep="," )
c<-rep(0,175)
c2<-rep(1,175)
plot(hs$ELDLogLikelihood,c, pch=16, col=ifelse(  hs$class <2, 'blue','red'),main="Striker-Goalie Real dataset", xlab='ELD, FD',ylab='',yaxt="n",xlim=c(0,25)) 
text(15,-0.1,"ELD distribution")
points(hs$ELDPrior,c2, pch=16, col=ifelse( hs$class <2, 'blue','red'), xlab='ELD, FD',ylab='',xaxt="n") 
text(5,0.9,"FD distribution")
dev.off()


pdf(file='C:/Users/Fatemeh/Downloads/Validation-Nov2015/Validation/NovData/SVELDvsFD.pdf') 
hs <- read.csv(file='C:/Users/Fatemeh/Downloads/Validation-Nov2015/Validation/NovData/SVELDvsFD.csv', head=TRUE, sep="," )
c<-rep(0,280)
c2<-rep(1,280)
plot(hs$ELDLogLikelihood,c, pch=16, col=ifelse(  hs$class <2, 'blue','red'),main="Low Correlation Synthetic dataset", xlab='ELD, FD*10',ylab='',yaxt="n") 
text(30,-0.1,"ELD distribution")
points(hs$ELDPrior*10,c2, pch=16, col=ifelse( hs$class <2, 'blue','red'), xlab='ELD, FD*10',ylab='',xaxt="n") 
text(8,0.9,"FD distribution")
dev.off()