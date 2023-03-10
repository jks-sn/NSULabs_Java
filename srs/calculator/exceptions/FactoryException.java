package srs.calculator.exceptions;

public class FactoryException extends CalcException{
        public FactoryException(String problemObjectName,String currentProblem){
        super(problemObjectName,currentProblem);
        }
}

