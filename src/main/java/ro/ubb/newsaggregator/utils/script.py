import sys
import nltk
from textblob import TextBlob
from newspaper import Article

nltk.download('punkt')

def analyze_article(url):
    article = Article(url)
    article.download()
    article.parse()
    article.nlp()

    print('Title:'+ article.title)
    if(len(article.authors) > 0):
        print('Authors:'+article.authors[0])
    print('Publication Date:'+str(article.publish_date))
    print('Summary:'+article.summary)

    analysis = TextBlob(article.text)
    print(analysis.polarity)
    print(f'Sentiment: {"positive" if analysis.polarity > 0 else "negative" if analysis.polarity < 0 else "neutral"}')

if __name__ == '__main__':
    if len(sys.argv) < 2:
        print('Please provide a URL as an argument.')
        sys.exit(1)

    url = sys.argv[1]
    analyze_article(url)
