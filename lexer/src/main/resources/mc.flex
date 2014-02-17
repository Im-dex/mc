%%

%class Lexer
%unicode
%line
%column
//%debug
//%standalone

LineEnding = \r|\n|\r\n
WhiteSpace = [ \t\f]

SingleLineComment = "//" .* LineEnding
MultiLIneComment = "/*" .* "*/"
Comment = {SingleLineComment} | {MultiLIneComment}

%%

[^] {}