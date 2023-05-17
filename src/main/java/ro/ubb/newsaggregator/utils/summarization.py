import pickle
import numpy
import nltk
from textblob import TextBlob
from newspaper import Article

nltk.download('punkt')
url = 'https://edition.cnn.com/2023/04/05/politics/trump-hush-money-indictment-bragg/index.html'

article = Article(url)

article.download()
article.parse()

article.nlp()

print('Title:'+ article.title)
print('Authors:'+article.authors[0])
print('Publication Date:'+str(article.publish_date))
print('Summary:'+article.summary)

analysis = TextBlob(article.text)
print(analysis.polarity)
print('Sentiment:'+"positive" if analysis.polarity > 0 else "negative" if analysis.polarity < 0 else "neutral")